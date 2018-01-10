import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class impro {

    static int getMostVisited(int n,int[] sprints)
    {
        int halfLength = sprints.length/2;
        int[] beginSprint = new int[halfLength];
        int[] endSprint = new int[halfLength];
        for (int i=0;i<sprints.length;i++){
            if (i%2==0){
                endSprint[i/2]=sprints[i];
            }
            else{
                beginSprint[i/2]=sprints[i];
            }
        }
        // Sort beginSprint and endSprint arrays
        Arrays.sort(beginSprint);
        Arrays.sort(endSprint);

        // currntPositions indicates number of visits at a time
        int currntPositions = 1, max_visits = 1, time = beginSprint[0];
        int i = 1, j = 0;

        // Similar to merge in merge sort to process
        // all events in sorted order
        while (i < n && j < n)
        {
            // If next event in sorted order is arrival,
            // increment count of visits
            if (beginSprint[i] <= endSprint[j])
            {
                currntPositions++;

                // Update max_visits if needed
                if (currntPositions > max_visits)
                {
                    max_visits = currntPositions;
                    time = beginSprint[i];
                }
                i++; //increment index of arrival array
            }
            else // If event is endSprint, decrement count
            { // of visits.
                currntPositions--;
                j++;
            }
        }

        return max_visits;
    }
    public static void main(String[] argv){
        int[] arr = new int[] { 1, 2, 2,3,1 };
        List<Integer> inputList = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.toList());
        int maxElement = Collections.max(inputList);
        int[] freqArray = new int[maxElement];
        for (int i=0;i<arr.length;i++){
            freqArray[arr[i]-1]++;
        }
        int maxFreq = 0;
        ArrayList<Integer> maxFreqs = new ArrayList<>();
        for (int i=0;i<freqArray.length;i++){
            if (freqArray[i]>maxFreq){
                maxFreq = freqArray[i];
                maxFreqs.clear();
                maxFreqs.add(i+1);
            }
            else if (freqArray[i]==maxFreq){
                maxFreqs.add(i+1);
            }
        }
        int min = arr.length;
        for (int element:maxFreqs){
            System.out.println(element);
            int lastIndex = inputList.lastIndexOf(element);
            int firstIndex = inputList.indexOf(element);
            System.out.println(inputList.lastIndexOf(element));
            System.out.println(inputList.indexOf(element));
            if (lastIndex-firstIndex+1<min){
                min = lastIndex-firstIndex+1;
            }
        }
        System.out.print(min);



    }
}
