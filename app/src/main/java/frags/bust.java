package frags;

public class bust {
    // Define animal first
    public static class animal {
        public void makesound() {
            System.out.println("Sound");
        }
    }

    // Then define dog, which extends animal
    public static class dog extends animal{
        public void makesound() {
            System.out.println("bark");
        }
        public void fetch(){
            System.out.println("e");
        }
    }

    public static void main(String[] args) {
        
        animal animal = new animal();
        animal.makesound();
        int a[] = new int[1];
        System.out.println(a[1]);
    }
}

