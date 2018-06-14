package com.testplayer.models;

public class AudioPlayListItem {
    private String name;
    private String url;

    public AudioPlayListItem(String uri) {
        this.url = uri;
    }

    public int getIntQuality(){
        final String str = url.replace("_v4.m3u8", "");
        StringBuilder result = new StringBuilder();

        for (int i=0; i<str.length(); i++){

            Character chars = str.charAt(i);

            if(chars != '.'){
                if(Character.isDigit(chars)){
                    result.append(chars);
                }
            }

        }
        return Integer.parseInt(result.toString());
    }

    public String getUrl() {
        return url;
    }
}
