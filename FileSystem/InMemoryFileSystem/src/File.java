public class File implements IFileSystemComponent, Comparable{
    private String content;
    private String name;
    private boolean isDir;
    File(String name){
        this.name = name;
        isDir = false;
    }

    @Override
    public boolean isDirectory() {
        return isDir;
    }

    public void addContent(String content){
        this.content = content;
    }

    public void appendContent(String content){
        this.content += content;
    }

    public String getContent(){
        return this.content;
    }
    @Override
    public int compareTo(Object o){
        return this.name.compareTo(((Directory) o).getName());
    }
}
