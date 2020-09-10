import java.io.Serializable;
import java.util.*;

public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {

    Entry<String> root;
    int length;

    static class Entry<T> implements Serializable {
        String elementName;
        boolean availableToAddLeftChildren;
        boolean availableToAddRightChildren;
        Entry<T> parent;
        Entry<T> leftChild;
        Entry<T> rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

    }

    public CustomTree() {
        this.root = new Entry<>("0");
        this.length = 0;
    }

    public void text() {
        Queue<Entry<String>> queue = new LinkedList<>();
        queue.add(root);
        Entry<String> current;
        while (queue.size() != 0) {
            current = queue.poll();
            System.out.println(current.elementName + " <---- parent is : " + (current.parent != null ? current.parent.elementName : "null"));
            if (current.leftChild != null)
                queue.add(current.leftChild);
            if (current.rightChild != null)
                queue.add(current.rightChild);
        }
    }

    @Override
    public boolean remove(Object o) {
        String s;
        try {
            s = (String) o;
        } catch (Exception e) {
            throw new UnsupportedOperationException();
        }
        Queue<Entry<String>> queue = new LinkedList<>();
        queue.add(root);
        Entry<String> deleted = null;
        while (queue.size() != 0) {
            Entry<String> current = queue.poll();
            if (current.elementName.equals(s)) {
                Entry<String> parent = current.parent;
                if (parent.rightChild == current)
                    parent.rightChild = null;
                else
                    parent.leftChild = null;
                deleted = current;
                break;
            } else {
                if (current.leftChild != null)
                    queue.add(current.leftChild);
                if (current.rightChild != null)
                    queue.add(current.rightChild);
            }
        }
        queue.clear();
        queue.add(deleted);
        while (queue.size() > 0) {
            Entry<String> current = queue.poll();
            --length;
            if (current.leftChild != null)
                queue.add(current.leftChild);
            if (current.rightChild != null)
                queue.add(current.rightChild);
        }
        if (!checkCanAdd()){
            refresh();
        }
        return false;
    }
    private void refresh() {
        Queue<Entry<String>> queue = new LinkedList<>();
        queue.add(root);
        while (queue.size() != 0) {
            Entry<String> current = queue.poll();
            if (current.leftChild != null)
                queue.add(current.leftChild);
            else {
                if (!current.availableToAddLeftChildren)
                    current.availableToAddLeftChildren = true;
            }
            if (current.rightChild != null)
                queue.add(current.rightChild);
            else {
                if (!current.availableToAddRightChildren)
                    current.availableToAddRightChildren = true;
            }
        }
    }

    private boolean checkCanAdd() {
        Queue<Entry<String>> queue = new LinkedList<>();
        queue.add(root);
        while (queue.size() != 0) {
            Entry<String> current = queue.poll();
            if (current.leftChild != null)
                queue.add(current.leftChild);
            else {
                if (current.availableToAddLeftChildren)
                    return true;
            }
            if (current.rightChild != null)
                queue.add(current.rightChild);
            else {
                if (current.availableToAddRightChildren)
                    return true;
            }
        }
        return false;
    }

    public boolean add(String s){
        Entry<String> entry = new Entry<>(s);
        Queue<Entry<String>> queue = new LinkedList<>();
        queue.add(root);
        while (queue.size() != 0) {
            Entry<String> current = queue.poll();
            if (current.leftChild != null)
                queue.add(current.leftChild);
            else if (current.availableToAddLeftChildren) {
                length++;
                entry.parent = current;
                current.leftChild = entry;
                current.availableToAddLeftChildren = false;
                return true;
            }
            if (current.rightChild != null)
                queue.add(current.rightChild);
            else if (current.availableToAddRightChildren) {
                length++;
                entry.parent = current;
                current.rightChild = entry;
                current.availableToAddRightChildren = false;
                return true;
            }
        }
        return false;
    }
    @Override
    public int size() {
        return length;
    }

    public String getParent(String s) {
        Queue<Entry<String>> queue = new LinkedList<>();
        queue.add(root);
        while (queue.size() != 0) {
            Entry<String> current = queue.poll();
            if (current.elementName.equals(s)){
                return current.parent.elementName;
            } else {
                if (current.leftChild != null)
                    queue.add(current.leftChild);
                if (current.rightChild != null)
                    queue.add(current.rightChild);
            }
        }
        return null;
    }

    @Override
    public String get(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }



}
