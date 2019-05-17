package com.yujian.mvp.ui.EventBus;

public interface EventBusTags {
    public interface UserProfile{
        public String MATCH = "MATCH";
        public String ADDMATCH = "ADDMATCH";
        public String CERTIFICATE = "CERTIFICATE";
        public String ADDCERTIFICATE = "ADDCERTIFICATE";
        public String PERSONALSTORY = "PERSONALSTORY";
        public String ADDPERSONALSTORY = "ADDPERSONALSTORY";
        public String PICTURESET = "PICTURESET";
        public String GOTOPICTURESETS = "GOTOPICTURESETS";
        public String GOTOPICTURESETSMANGEE = "GOTOPICTURESETSMANGEE";
        public String GOTOINTRODUCE = "GOTOINTRODUCE";
        public String GOTOADDFEEDBACK = "GOTOADDFEEDBACK";
        public String GOTOVISIT = "GOTOVISIT";
        public String GOTOFANS = "GOTOFANS";
    }

    public interface AdapterClickable{
        public interface UserDynamicListAdapter{
            public String PRAISECOUNTICON = "PRAISECOUNTICON";
            public String COMMENTCOUNTICON = "COMMENTCOUNTICON";
            public String SHARENUMICON = "SHARENUMICON";
            public String HEAD = "HEAD";
            public String TOPIC = "TOPIC";
            public String NAME = "NAME";
        }

        public interface ReadMoreTopicListAdapter{
            public String ITEM = "ITEM";
            public String HEADER = "HEADER";
        }
    }
}
