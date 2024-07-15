import java.util.*;

class FileSystem {
    private Directory root;
    public FileSystem() {
        // Replace this placeholder return statement with your code
        root = new Directory("/");
    }
    public List <String> ls(String path) {
        // Replace this placeholder return statement with your code
        String[] paths = path.split("/");
        Directory currDirectory = root;

        for(int i = 1; i < paths.length; i++){
            if(currDirectory.directories.containsKey(paths[i])){
                currDirectory = currDirectory.directories.get(paths[i]);
            }else{
                return new ArrayList<>();
            }
        }

        ArrayList<String> result = new ArrayList<>();
        if(currDirectory.isDirectory()){
            result.addAll(currDirectory.directories.keySet());
            result.addAll(currDirectory.files.keySet());
            result.sort((a, b) -> a.compareTo(b));
        }else{
            result.addAll(currDirectory.files.keySet());
            result.sort((a, b) -> a.compareTo(b));
        }
        return result;
    }

    public void mkdir(String path) {
        // Replace this placeholder return statement with your code
        String[] fsComponents = path.split("/");
        Directory currentDir = root;

        for(String component: fsComponents){
            if(component.isEmpty()){
                continue;
            }
            if(currentDir.directories.containsKey(component)){
                currentDir = currentDir.directories.get(component);
            }else{
                Directory newDirectory = new Directory(component);
                currentDir.directories.put(component, newDirectory);
                currentDir = newDirectory;
            }
        }
    }

    public void addContentToFile(String filePath, String content) {
        // Replace this placeholder return statement with your code
        String[] fsComponentNames = filePath.split("/");
        Directory currentDirectory = root;
        int i;
        for(i = 1; i < fsComponentNames.length - 1; i++){
            String name = fsComponentNames[i];
            if(currentDirectory.directories.containsKey(name)){
                currentDirectory = currentDirectory.directories.get(name);
            }else{
                Directory newDirectory = new Directory(name);
                currentDirectory.directories.put(name, newDirectory);
                currentDirectory = newDirectory;
            }
        }


        if(currentDirectory.files.containsKey(fsComponentNames[i])){
            File file = currentDirectory.files.get(fsComponentNames[i]);
            if(file.getContent() != null){
                file.appendContent(content);
            }else{
                file.addContent(content);
            }
        }else{
            File newFile = new File(fsComponentNames[i]);
            newFile.addContent(content);
            currentDirectory.files.put(fsComponentNames[i], newFile);
        }
    }

    public String readContentFromFile(String filePath) {
        // Replace this placeholder return statement with your code
        String[] paths = filePath.split("/");
        Directory currDirectory = root;

        for(int i = 1; i < paths.length-1; i++){
            if(currDirectory.directories.containsKey(paths[i])){
                currDirectory = currDirectory.directories.get(paths[i]);
            }else{
                return "";
            }
        }

        if(!currDirectory.files.containsKey(paths[paths.length - 1])){
            return "";
        }
        return currDirectory.files.get(paths[paths.length - 1]).getContent();
    }
}


public class Main {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        fileSystem.ls("/");
        fileSystem.mkdir("/a/b/c");
        fileSystem.mkdir("/a/b/d");
        fileSystem.mkdir("/a/b/e");
        List<String> list = fileSystem.ls("/a/b");
        list.forEach(System.out::println);
        fileSystem.addContentToFile("/a/b/c/d", "educative");
        System.out.println(fileSystem.readContentFromFile("/a/b/c/d"));
    }
}