import java.util.*;

public class Directory implements IFileSystemComponent, Comparable {
    Map<String, Directory> directories;
    Map<String, File> files;
    String name;
    private boolean isDir;
    Directory(String name){
        this.name = name;
        isDir = true;
        directories = new HashMap<>();
        files = new HashMap<>();
    }

    @Override
    public boolean isDirectory(){
        return isDir;
    }

    public String getName() {
        return name;
    }
    @Override
    public int compareTo(Object o){
        return this.name.compareTo(((Directory) o).getName());
    }

}
