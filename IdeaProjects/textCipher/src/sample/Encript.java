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
}
