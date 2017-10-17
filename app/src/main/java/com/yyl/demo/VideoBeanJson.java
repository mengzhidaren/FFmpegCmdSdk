package com.yyl.demo;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * Created by yuyunlong on 2017/10/11/011.
 */

public class VideoBeanJson {


    public static VideoBeanJson getJson(String json) {
        if (TextUtils.isEmpty(json)) return null;
        return new Gson().fromJson(json, VideoBeanJson.class);
    }

    public long getTime() {
        if (format==null)return 0;
        String duration = format.getDuration();
        float aFloat = Float.valueOf(duration);
        return (long) (aFloat * 1000);
    }

    /**
     * format : {"filename":"/storage/emulated/0/test1.mp4","nb_streams":2,"nb_programs":0,"format_name":"mov,mp4,m4a,3gp,3g2,mj2","start_time":"0.000000","duration":"15.054000","size":"1306580","bit_rate":"694343","probe_score":100,"tags":{"major_brand":"isom","minor_version":"512","compatible_brands":"isomiso2avc1mp41","encoder":"Lavf56.4.101"}}
     */
    private FormatBean format;

    public FormatBean getFormat() {
        return format;
    }

    public void setFormat(FormatBean format) {
        this.format = format;
    }

    public static class FormatBean {
        /**
         * filename : /storage/emulated/0/test1.mp4
         * nb_streams : 2
         * nb_programs : 0
         * format_name : mov,mp4,m4a,3gp,3g2,mj2
         * start_time : 0.000000
         * duration : 15.054000
         * size : 1306580
         * bit_rate : 694343
         * probe_score : 100
         * tags : {"major_brand":"isom","minor_version":"512","compatible_brands":"isomiso2avc1mp41","encoder":"Lavf56.4.101"}
         */

        private String filename;
        private int nb_streams;
        private int nb_programs;
        private String format_name;
        private String start_time;
        private String duration;
        private String size;
        private String bit_rate;
        private int probe_score;
        private TagsBean tags;

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public int getNb_streams() {
            return nb_streams;
        }

        public void setNb_streams(int nb_streams) {
            this.nb_streams = nb_streams;
        }

        public int getNb_programs() {
            return nb_programs;
        }

        public void setNb_programs(int nb_programs) {
            this.nb_programs = nb_programs;
        }

        public String getFormat_name() {
            return format_name;
        }

        public void setFormat_name(String format_name) {
            this.format_name = format_name;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getBit_rate() {
            return bit_rate;
        }

        public void setBit_rate(String bit_rate) {
            this.bit_rate = bit_rate;
        }

        public int getProbe_score() {
            return probe_score;
        }

        public void setProbe_score(int probe_score) {
            this.probe_score = probe_score;
        }

        public TagsBean getTags() {
            return tags;
        }

        public void setTags(TagsBean tags) {
            this.tags = tags;
        }

        public static class TagsBean {
            /**
             * major_brand : isom
             * minor_version : 512
             * compatible_brands : isomiso2avc1mp41
             * encoder : Lavf56.4.101
             */

            private String major_brand;
            private String minor_version;
            private String compatible_brands;
            private String encoder;

            public String getMajor_brand() {
                return major_brand;
            }

            public void setMajor_brand(String major_brand) {
                this.major_brand = major_brand;
            }

            public String getMinor_version() {
                return minor_version;
            }

            public void setMinor_version(String minor_version) {
                this.minor_version = minor_version;
            }

            public String getCompatible_brands() {
                return compatible_brands;
            }

            public void setCompatible_brands(String compatible_brands) {
                this.compatible_brands = compatible_brands;
            }

            public String getEncoder() {
                return encoder;
            }

            public void setEncoder(String encoder) {
                this.encoder = encoder;
            }

            @Override
            public String toString() {
                return "TagsBean{" +
                        "major_brand='" + major_brand + '\'' +
                        ", minor_version='" + minor_version + '\'' +
                        ", compatible_brands='" + compatible_brands + '\'' +
                        ", encoder='" + encoder + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "FormatBean{" +
                    "filename='" + filename + '\'' +
                    ", nb_streams=" + nb_streams +
                    ", nb_programs=" + nb_programs +
                    ", format_name='" + format_name + '\'' +
                    ", start_time='" + start_time + '\'' +
                    ", duration='" + duration + '\'' +
                    ", size='" + size + '\'' +
                    ", bit_rate='" + bit_rate + '\'' +
                    ", probe_score=" + probe_score +
                    ", tags=" + tags +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "VideoBeanJson{" +
                "format=" + format +
                '}';
    }
}
