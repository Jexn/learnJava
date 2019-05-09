public class SelectSort{
    public static <E extends Comparable<E>> void selectSort(E[] list){
        E currentMin;
        int currentMinOfIndex;
        for (int i = 0; i < list.length; i++){
            currentMin = list[i];
            currentMinOfIndex = i;
            for (int j = i+1; j < list.length;j++){
                if (currentMin.compareTo(list[j])>0){
                    currentMin = list[j];
                    currentMinOfIndex = j;
                }
            }
            list[currentMinOfIndex] = list[i];
            list[i] = currentMin;
        }
    }
}