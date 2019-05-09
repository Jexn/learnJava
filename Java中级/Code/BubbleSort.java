public class BubbleSort{
    public static <E extends Comparable<E>> void bubbleSort(E[] list) {
        boolean needNextPas = true;
        for (int i = 1; i < list.length && needNextPas; i++) {
            needNextPas = false;
            for (int j = 0; j < list.length - i; j++){
                if (list[j].compareTo(list[j+1]) > 0) {
                    E temp = list[j];
                    list[j] = list[j+1];
                    list[j+1] = temp;
                    needNextPas = true;
                }
            }
        }
    }
}