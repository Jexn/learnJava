public class InsertionSort{
    public static <E extends Comparable<E>> void insertionSort(E[] list){
        for (int i = 0; i < list.length; i++){
            E currentElement = list[i];
            int k;
            for (k = i - 1; k >= 0 && list[k].compareTo(currentElement) > 0; k--){
                list[k+1] = list[k];
            }
            list[k+1] = currentElement;
        }
    }
}