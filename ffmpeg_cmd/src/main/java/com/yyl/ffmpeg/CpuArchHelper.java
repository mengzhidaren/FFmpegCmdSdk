package com.yyl.ffmpeg;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CpuArchHelper {
    private static String TAG = "CpuArchHelper";
    private static final int EM_386 = 3;
    private static final int EM_MIPS = 8;
    private static final int EM_ARM = 40;
    private static final int EM_X86_64 = 62;
    private static final int EM_AARCH64 = 183;
    private static final int ELF_HEADER_SIZE = 52;
    private static final int SECTION_HEADER_SIZE = 40;
    private static final int SHT_ARM_ATTRIBUTES = 0x70000003;

    public static class MachineSpecs {
        public boolean hasNeon;
        public boolean hasFpu;
        public boolean hasArmV6;
        public boolean hasArmV7;
        public boolean hasMips;
        public boolean hasX86;
        public boolean is64bits;
        public float bogoMIPS;
        public int processors;
        public float frequency; /* in MHz */
    }

    private static class ElfData {
        ByteOrder order;
        boolean is64bits;
        int e_machine;
        int e_shoff;
        int e_shnum;
        int sh_offset;
        int sh_size;
        String att_arch;
        boolean att_fpu;
    }

    public static void checkCPU(Context context) {
        ElfData elf = null;
        boolean elfHasX86 = false;
        boolean elfHasArm = false;
        boolean elfHasMips = false;
        boolean elfIs64bits = false;
        final File lib = searchLibrary(context.getApplicationInfo());
        if (lib != null && (elf = readLib(lib)) != null) {
            elfHasX86 = elf.e_machine == EM_386 || elf.e_machine == EM_X86_64;
            elfHasArm = elf.e_machine == EM_ARM || elf.e_machine == EM_AARCH64;
            elfHasMips = elf.e_machine == EM_MIPS;
            elfIs64bits = elf.is64bits;

            Log.i(TAG, "ELF ABI = " + (elfHasArm ? "arm" : elfHasX86 ? "x86" : "mips") + ", " +
                    (elfIs64bits ? "64bits" : "32bits"));
            Log.i(TAG, "ELF arch = " + elf.att_arch);
            Log.i(TAG, "ELF fpu = " + elf.att_fpu);
        } else {
            Log.i(TAG, "WARNING: Unable to read libyylffmpeg.so; cannot check device ABI!");
        }
    }


    /**
     * '*' prefix means it's unsupported
     */
    private final static String[] CPU_archs = {"*Pre-v4", "*v4", "*v4T",
            "v5T", "v5TE", "v5TEJ",
            "v6", "v6KZ", "v6T2", "v6K", "v7",
            "*v6-M", "*v6S-M", "*v7E-M", "*v8"};

    private static File searchLibrary(ApplicationInfo applicationInfo) {
        // Search for library path
        String[] libraryPaths;
        if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
            final String property = System.getProperty("java.library.path");
            libraryPaths = property.split(":");
        } else {
            libraryPaths = new String[1];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
                libraryPaths[0] = applicationInfo.nativeLibraryDir;
            else
                libraryPaths[0] = applicationInfo.dataDir + "/lib";
        }
        Log.i(TAG, "find library path=" + libraryPaths[0]);
        if (libraryPaths[0] == null) {
            Log.i(TAG, "can't find library path");
            return null;
        }
        getFileList(libraryPaths[0]);
        // Search for libyylffmpeg.so
        File lib;
        for (String libraryPath : libraryPaths) {
            lib = new File(libraryPath, "libyylffmpeg.so");
            if (lib.exists() && lib.canRead())
                return lib;
        }


        Log.i(TAG, "WARNING: Can't find shared library");
        return null;
    }

    public static void getFileList(String strPath) {
        System.out.println("getFileList=" + strPath);
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    System.out.println("fileName 1=" + fileName);
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else { // 判断文件名是否以.avi结尾
                    System.out.println("fileName 2=" + fileName);
                }
            }
        }
    }

    private static ElfData readLib(File file) {
        RandomAccessFile in = null;
        try {
            in = new RandomAccessFile(file, "r");

            ElfData elf = new ElfData();
            if (!readHeader(in, elf))
                return null;

            switch (elf.e_machine) {
                case EM_386:
                case EM_MIPS:
                case EM_X86_64:
                case EM_AARCH64:
                    return elf;
                case EM_ARM:
                    in.close();
                    in = new RandomAccessFile(file, "r");
                    if (!readSection(in, elf))
                        return null;
                    in.close();
                    in = new RandomAccessFile(file, "r");
                    if (!readArmAttributes(in, elf))
                        return null;
                    break;
                default:
                    return null;
            }
            return elf;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    private static boolean readHeader(RandomAccessFile in, ElfData elf) throws IOException {
        // http://www.sco.com/developers/gabi/1998-04-29/ch4.eheader.html
        byte[] bytes = new byte[ELF_HEADER_SIZE];
        in.readFully(bytes);
        if (bytes[0] != 127 ||
                bytes[1] != 'E' ||
                bytes[2] != 'L' ||
                bytes[3] != 'F' ||
                (bytes[4] != 1 && bytes[4] != 2)) {
            Log.e(TAG, "ELF header invalid");
            return false;
        }

        elf.is64bits = bytes[4] == 2;
        elf.order = bytes[5] == 1
                ? ByteOrder.LITTLE_ENDIAN // ELFDATA2LSB
                : ByteOrder.BIG_ENDIAN;   // ELFDATA2MSB

        // wrap bytes in a ByteBuffer to force endianess
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(elf.order);

        elf.e_machine = buffer.getShort(18);    /* Architecture */
        elf.e_shoff = buffer.getInt(32);        /* Section header table file offset */
        elf.e_shnum = buffer.getShort(48);      /* Section header table entry count */
        return true;
    }

    private static boolean readSection(RandomAccessFile in, ElfData elf) throws IOException {
        byte[] bytes = new byte[SECTION_HEADER_SIZE];
        in.seek(elf.e_shoff);

        for (int i = 0; i < elf.e_shnum; ++i) {
            in.readFully(bytes);

            // wrap bytes in a ByteBuffer to force endianess
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            buffer.order(elf.order);

            int sh_type = buffer.getInt(4); /* Section type */
            if (sh_type != SHT_ARM_ATTRIBUTES)
                continue;

            elf.sh_offset = buffer.getInt(16);  /* Section file offset */
            elf.sh_size = buffer.getInt(20);    /* Section size in bytes */
            return true;
        }

        return false;
    }

    private static boolean readArmAttributes(RandomAccessFile in, ElfData elf) throws IOException {
        byte[] bytes = new byte[elf.sh_size];
        in.seek(elf.sh_offset);
        in.readFully(bytes);

        // wrap bytes in a ByteBuffer to force endianess
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(elf.order);

        //http://infocenter.arm.com/help/topic/com.arm.doc.ihi0044e/IHI0044E_aaelf.pdf
        //http://infocenter.arm.com/help/topic/com.arm.doc.ihi0045d/IHI0045D_ABI_addenda.pdf
        if (buffer.get() != 'A') // format-version
            return false;

        // sub-sections loop
        while (buffer.remaining() > 0) {
            int start_section = buffer.position();
            int length = buffer.getInt();
            String vendor = getString(buffer);
            if (vendor.equals("aeabi")) {
                // tags loop
                while (buffer.position() < start_section + length) {
                    int start = buffer.position();
                    int tag = buffer.get();
                    int size = buffer.getInt();
                    // skip if not Tag_File, we don't care about others
                    if (tag != 1) {
                        buffer.position(start + size);
                        continue;
                    }

                    // attributes loop
                    while (buffer.position() < start + size) {
                        tag = getUleb128(buffer);
                        if (tag == 6) { // CPU_arch
                            int arch = getUleb128(buffer);
                            elf.att_arch = CPU_archs[arch];
                        } else if (tag == 27) { // ABI_HardFP_use
                            getUleb128(buffer);
                            elf.att_fpu = true;
                        } else {
                            // string for 4=CPU_raw_name / 5=CPU_name / 32=compatibility
                            // string for >32 && odd tags
                            // uleb128 for other
                            tag %= 128;
                            if (tag == 4 || tag == 5 || tag == 32 || (tag > 32 && (tag & 1) != 0))
                                getString(buffer);
                            else
                                getUleb128(buffer);
                        }
                    }
                }
                break;
            }
        }
        return true;
    }

    private static String getString(ByteBuffer buffer) {
        StringBuilder sb = new StringBuilder(buffer.limit());
        while (buffer.remaining() > 0) {
            char c = (char) buffer.get();
            if (c == 0)
                break;
            sb.append(c);
        }
        return sb.toString();
    }

    private static int getUleb128(ByteBuffer buffer) {
        int ret = 0;
        int c;
        do {
            ret <<= 7;
            c = buffer.get();
            ret |= c & 0x7f;
        } while ((c & 0x80) > 0);

        return ret;
    }
}
