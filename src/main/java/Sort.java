public class Sort {


    public static int[] sort(int[] arr){
        int length = arr.length;
        boolean flag = false;
        for (int i = 0; i < length; i++) {
            flag = false;
            for (int j = 0;j < length-(i+1);j++) {
                if (arr[j] > arr [j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] =temp;
                    flag = true;
                }
            }
            if (!flag){
                break;
            }
        }
        return arr;
    }


    public static void main(String[] args) {
        int[] arr = {5,2,6,3,1,2};
        int[] res = Sort.sort(arr);

        for (int i = 0; i < res.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
