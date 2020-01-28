package sample;

class Encript {
    private String inputText;
    private int key;

    public Encript(String inputText, int key) {
        this.key = key;
        this.inputText = inputText;
    }
    public String getInput() {
        return inputText;
    }
    public int getKey() {
        return key;
    }
    public void setInput(String inputText) {
        this.inputText = inputText;

    }
    public void setKey(int key) {
        this.key = key;
    }
    public String encrypt() {
        String output="";

        char c;

        for(int i=0;i < inputText.length();i++) {
            c = inputText.charAt(i);

            if(c >= 'a' && c <= 'z') {
                c = (char)(c + key);
                if(c > 'z') {
                    c=(char)(c-'z' + 'a' -1);

                }
                output += c;
            }
            else if(c >= 'A' && c <= 'Z') {
                c = (char)(c + key);
                if( c >= 'Z') {
                    c = (char)(c-'Z'+ 'A'-1);
                }
                output += c;
            }

            else {
                output += c;
            }
        }
        return output;


    }
    public String decrypt() {
        String output="";

        char c;

        for(int i=0;i < inputText.length();i++) {
            c = inputText.charAt(i);

            if(c >= 'a' && c <= 'z') {
                c = (char)(c - key);
                if(c > 'z') {
                    c=(char)(c+'z' - 'a' +1);

                }
                output += c;
            }
            else if(c >= 'A' && c <= 'Z') {
                c = (char)(c - key);
                if( c >= 'Z') {
                    c = (char)(c+'Z'- 'A'+1);
                }
                output += c;
            }

            else {
                output += c;
            }
        }
        return output;


    }
}
