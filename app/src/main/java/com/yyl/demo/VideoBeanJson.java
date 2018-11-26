package com.yyl.demo;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yuyunlong on 2017/10/11/011.
 * {
 * "programs": [
 * <p>
 * ],
 * "streams": [
 * {
 * "index": 0,
 * "codec_name": "h264",
 * "codec_long_name": "unknown",
 * "profile": "578",
 * "codec_type": "video",
 * "codec_time_base": "1/50",
 * "codec_tag_string": "avc1",
 * "codec_tag": "0x31637661",
 * "width": 730,
 * "height": 410,
 * "coded_width": 736,
 * "coded_height": 416,
 * "has_b_frames": 0,
 * "sample_aspect_ratio": "1:1",
 * "display_aspect_ratio": "73:41",
 * "pix_fmt": "yuv420p",
 * "level": 30,
 * "chroma_location": "left",
 * "refs": 1,
 * "is_avc": "true",
 * "nal_length_size": "4",
 * "r_frame_rate": "25/1",
 * "avg_frame_rate": "25/1",
 * "time_base": "1/25",
 * "start_pts": 0,
 * "start_time": "0.000000",
 * "duration_ts": 728,
 * "duration": "29.120000",
 * "bit_rate": "1887975",
 * "bits_per_raw_sample": "8",
 * "nb_frames": "728",
 * "disposition": {
 * "default": 1,
 * "dub": 0,
 * "original": 0,
 * "comment": 0,
 * "lyrics": 0,
 * "karaoke": 0,
 * "forced": 0,
 * "hearing_impaired": 0,
 * "visual_impaired": 0,
 * "clean_effects": 0,
 * "attached_pic": 0,
 * "timed_thumbnails": 0
 * },
 * "tags": {
 * "creation_time": "1970-01-01T00:00:00.000000Z",
 * "language": "und",
 * "handler_name": "VideoHandler"
 * }
 * }
 * ]
 * }
 */

public class VideoBeanJson {


    //    private List<?> programs;
    private List<StreamsBean> streams;

    public static VideoBeanJson getJson(String json) {
        if (TextUtils.isEmpty(json)) return null;
        return new Gson().fromJson(json, VideoBeanJson.class);
    }

    public long getTime() {
        if (streams != null && streams.size() > 0) {
            String duration = streams.get(0).getDuration();
            float aFloat = Float.valueOf(duration);
            return (long) (aFloat * 1000);
        }
        return 0;
    }


    public List<StreamsBean> getStreams() {
        return streams;
    }

    public void setStreams(List<StreamsBean> streams) {
        this.streams = streams;
    }


    public static class StreamsBean {
        /**
         * index : 0
         * codec_name : h264
         * codec_long_name : unknown
         * profile : 578
         * codec_type : video
         * codec_time_base : 1/50
         * codec_tag_string : avc1
         * codec_tag : 0x31637661
         * width : 730
         * height : 410
         * coded_width : 736
         * coded_height : 416
         * has_b_frames : 0
         * sample_aspect_ratio : 1:1
         * display_aspect_ratio : 73:41
         * pix_fmt : yuv420p
         * level : 30
         * chroma_location : left
         * refs : 1
         * is_avc : true
         * nal_length_size : 4
         * r_frame_rate : 25/1
         * avg_frame_rate : 25/1
         * time_base : 1/25
         * start_pts : 0
         * start_time : 0.000000
         * duration_ts : 728
         * duration : 29.120000
         * bit_rate : 1887975
         * bits_per_raw_sample : 8
         * nb_frames : 728
         * disposition : {"default":1,"dub":0,"original":0,"comment":0,"lyrics":0,"karaoke":0,"forced":0,"hearing_impaired":0,"visual_impaired":0,"clean_effects":0,"attached_pic":0,"timed_thumbnails":0}
         * tags : {"creation_time":"1970-01-01T00:00:00.000000Z","language":"und","handler_name":"VideoHandler"}
         */

        private int index;
        private String codec_name;
        private String codec_long_name;
        private String profile;
        private String codec_type;
        private String codec_time_base;
        private String codec_tag_string;
        private String codec_tag;
        private int width;
        private int height;
        private int coded_width;
        private int coded_height;
        private int has_b_frames;
        private String sample_aspect_ratio;
        private String display_aspect_ratio;
        private String pix_fmt;
        private int level;
        private String chroma_location;
        private int refs;
        private String is_avc;
        private String nal_length_size;
        private String r_frame_rate;
        private String avg_frame_rate;
        private String time_base;
        private int start_pts;
        private String start_time;
        private int duration_ts;
        private String duration = "0";
        private String bit_rate;
        private String bits_per_raw_sample;
        private String nb_frames;
//        private DispositionBean disposition;
//        private TagsBean tags;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCodec_name() {
            return codec_name;
        }

        public void setCodec_name(String codec_name) {
            this.codec_name = codec_name;
        }

        public String getCodec_long_name() {
            return codec_long_name;
        }

        public void setCodec_long_name(String codec_long_name) {
            this.codec_long_name = codec_long_name;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getCodec_type() {
            return codec_type;
        }

        public void setCodec_type(String codec_type) {
            this.codec_type = codec_type;
        }

        public String getCodec_time_base() {
            return codec_time_base;
        }

        public void setCodec_time_base(String codec_time_base) {
            this.codec_time_base = codec_time_base;
        }

        public String getCodec_tag_string() {
            return codec_tag_string;
        }

        public void setCodec_tag_string(String codec_tag_string) {
            this.codec_tag_string = codec_tag_string;
        }

        public String getCodec_tag() {
            return codec_tag;
        }

        public void setCodec_tag(String codec_tag) {
            this.codec_tag = codec_tag;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getCoded_width() {
            return coded_width;
        }

        public void setCoded_width(int coded_width) {
            this.coded_width = coded_width;
        }

        public int getCoded_height() {
            return coded_height;
        }

        public void setCoded_height(int coded_height) {
            this.coded_height = coded_height;
        }

        public int getHas_b_frames() {
            return has_b_frames;
        }

        public void setHas_b_frames(int has_b_frames) {
            this.has_b_frames = has_b_frames;
        }

        public String getSample_aspect_ratio() {
            return sample_aspect_ratio;
        }

        public void setSample_aspect_ratio(String sample_aspect_ratio) {
            this.sample_aspect_ratio = sample_aspect_ratio;
        }

        public String getDisplay_aspect_ratio() {
            return display_aspect_ratio;
        }

        public void setDisplay_aspect_ratio(String display_aspect_ratio) {
            this.display_aspect_ratio = display_aspect_ratio;
        }

        public String getPix_fmt() {
            return pix_fmt;
        }

        public void setPix_fmt(String pix_fmt) {
            this.pix_fmt = pix_fmt;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getChroma_location() {
            return chroma_location;
        }

        public void setChroma_location(String chroma_location) {
            this.chroma_location = chroma_location;
        }

        public int getRefs() {
            return refs;
        }

        public void setRefs(int refs) {
            this.refs = refs;
        }

        public String getIs_avc() {
            return is_avc;
        }

        public void setIs_avc(String is_avc) {
            this.is_avc = is_avc;
        }

        public String getNal_length_size() {
            return nal_length_size;
        }

        public void setNal_length_size(String nal_length_size) {
            this.nal_length_size = nal_length_size;
        }

        public String getR_frame_rate() {
            return r_frame_rate;
        }

        public void setR_frame_rate(String r_frame_rate) {
            this.r_frame_rate = r_frame_rate;
        }

        public String getAvg_frame_rate() {
            return avg_frame_rate;
        }

        public void setAvg_frame_rate(String avg_frame_rate) {
            this.avg_frame_rate = avg_frame_rate;
        }

        public String getTime_base() {
            return time_base;
        }

        public void setTime_base(String time_base) {
            this.time_base = time_base;
        }

        public int getStart_pts() {
            return start_pts;
        }

        public void setStart_pts(int start_pts) {
            this.start_pts = start_pts;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public int getDuration_ts() {
            return duration_ts;
        }

        public void setDuration_ts(int duration_ts) {
            this.duration_ts = duration_ts;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getBit_rate() {
            return bit_rate;
        }

        public void setBit_rate(String bit_rate) {
            this.bit_rate = bit_rate;
        }

        public String getBits_per_raw_sample() {
            return bits_per_raw_sample;
        }

        public void setBits_per_raw_sample(String bits_per_raw_sample) {
            this.bits_per_raw_sample = bits_per_raw_sample;
        }

        public String getNb_frames() {
            return nb_frames;
        }

        public void setNb_frames(String nb_frames) {
            this.nb_frames = nb_frames;
        }

//        public DispositionBean getDisposition() {
//            return disposition;
//        }
//
//        public void setDisposition(DispositionBean disposition) {
//            this.disposition = disposition;
//        }
//
//        public TagsBean getTags() {
//            return tags;
//        }
//
//        public void setTags(TagsBean tags) {
//            this.tags = tags;
//        }

        public static class DispositionBean {
            /**
             * default : 1
             * dub : 0
             * original : 0
             * comment : 0
             * lyrics : 0
             * karaoke : 0
             * forced : 0
             * hearing_impaired : 0
             * visual_impaired : 0
             * clean_effects : 0
             * attached_pic : 0
             * timed_thumbnails : 0
             */

            @SerializedName("default")
            private int defaultX;
            private int dub;
            private int original;
            private int comment;
            private int lyrics;
            private int karaoke;
            private int forced;
            private int hearing_impaired;
            private int visual_impaired;
            private int clean_effects;
            private int attached_pic;
            private int timed_thumbnails;

            public int getDefaultX() {
                return defaultX;
            }

            public void setDefaultX(int defaultX) {
                this.defaultX = defaultX;
            }

            public int getDub() {
                return dub;
            }

            public void setDub(int dub) {
                this.dub = dub;
            }

            public int getOriginal() {
                return original;
            }

            public void setOriginal(int original) {
                this.original = original;
            }

            public int getComment() {
                return comment;
            }

            public void setComment(int comment) {
                this.comment = comment;
            }

            public int getLyrics() {
                return lyrics;
            }

            public void setLyrics(int lyrics) {
                this.lyrics = lyrics;
            }

            public int getKaraoke() {
                return karaoke;
            }

            public void setKaraoke(int karaoke) {
                this.karaoke = karaoke;
            }

            public int getForced() {
                return forced;
            }

            public void setForced(int forced) {
                this.forced = forced;
            }

            public int getHearing_impaired() {
                return hearing_impaired;
            }

            public void setHearing_impaired(int hearing_impaired) {
                this.hearing_impaired = hearing_impaired;
            }

            public int getVisual_impaired() {
                return visual_impaired;
            }

            public void setVisual_impaired(int visual_impaired) {
                this.visual_impaired = visual_impaired;
            }

            public int getClean_effects() {
                return clean_effects;
            }

            public void setClean_effects(int clean_effects) {
                this.clean_effects = clean_effects;
            }

            public int getAttached_pic() {
                return attached_pic;
            }

            public void setAttached_pic(int attached_pic) {
                this.attached_pic = attached_pic;
            }

            public int getTimed_thumbnails() {
                return timed_thumbnails;
            }

            public void setTimed_thumbnails(int timed_thumbnails) {
                this.timed_thumbnails = timed_thumbnails;
            }
        }

        public static class TagsBean {
            /**
             * creation_time : 1970-01-01T00:00:00.000000Z
             * language : und
             * handler_name : VideoHandler
             */

            private String creation_time;
            private String language;
            private String handler_name;

            public String getCreation_time() {
                return creation_time;
            }

            public void setCreation_time(String creation_time) {
                this.creation_time = creation_time;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getHandler_name() {
                return handler_name;
            }

            public void setHandler_name(String handler_name) {
                this.handler_name = handler_name;
            }
        }
    }
}
