import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SortingVisualization extends JPanel {

    private int[] array;
    private int delay = 100; // Delay in milliseconds for visualization
    private String algorithm = "Bubble Sort"; // Default sorting algorithm

    public SortingVisualization(int size) {
        array = new int[size];
        generateRandomArray();
    }

    // Generate a random array
    private void generateRandomArray() {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(400) + 10; // Random values between 10 and 400
        }
    }

    // Bubble Sort Algorithm with Visualization
    public void bubbleSort() {
        new Thread(() -> {
            for (int i = 0; i < array.length - 1; i++) {
                for (int j = 0; j < array.length - i - 1; j++) {
                    if (array[j] > array[j + 1]) {
                        // Swap elements
                        int temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;

                        // Repaint the panel to visualize the change
                        repaint();
                        try {
                            Thread.sleep(delay); // Pause for visualization
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    // Selection Sort Algorithm with Visualization
    public void selectionSort() {
        new Thread(() -> {
            for (int i = 0; i < array.length - 1; i++) {
                int minIndex = i;
                for (int j = i + 1; j < array.length; j++) {
                    if (array[j] < array[minIndex]) {
                        minIndex = j;
                    }
                }
                // Swap elements
                int temp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = temp;

                repaint();
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // Insertion Sort Algorithm with Visualization
    public void insertionSort() {
        new Thread(() -> {
            for (int i = 1; i < array.length; i++) {
                int key = array[i];
                int j = i - 1;
                while (j >= 0 && array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                }
                array[j + 1] = key;

                repaint();
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // Merge Sort Algorithm with Visualization
    public void mergeSort() {
        new Thread(() -> {
            mergeSortHelper(0, array.length - 1);
        }).start();
    }

    private void mergeSortHelper(int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSortHelper(left, mid);
            mergeSortHelper(mid + 1, right);

            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = array[i++];
        }

        while (j <= right) {
            temp[k++] = array[j++];
        }

        for (i = left, k = 0; i <= right; i++, k++) {
            array[i] = temp[k];
            repaint();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Quick Sort Algorithm with Visualization
    public void quickSort() {
        new Thread(() -> {
            quickSortHelper(0, array.length - 1);
        }).start();
    }

    private void quickSortHelper(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);

            quickSortHelper(low, pi - 1);
            quickSortHelper(pi + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                // Swap
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        // Swap pivot
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        repaint();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return i + 1;
    }

    // Paint the array as bars
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        int width = getWidth() / array.length;
        for (int i = 0; i < array.length; i++) {
            g.setColor(Color.CYAN);
            g.fillRect(i * width, getHeight() - array[i], width - 2, array[i]);
        }
    }

    // Main method to run the visualization
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sorting Visualization");
        SortingVisualization panel = new SortingVisualization(50); // Array size

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.add(panel);
        frame.setVisible(true);

        // Create a dropdown menu for selecting sorting algorithms
        String[] algorithms = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort"};
        JComboBox<String> comboBox = new JComboBox<>(algorithms);
        comboBox.addActionListener(e -> {
            String selectedAlgorithm = (String) comboBox.getSelectedItem();
            switch (selectedAlgorithm) {
                case "Bubble Sort":
                    panel.bubbleSort();
                    break;
                case "Selection Sort":
                    panel.selectionSort();
                    break;
                case "Insertion Sort":
                    panel.insertionSort();
                    break;
                case "Merge Sort":
                    panel.mergeSort();
                    break;
                case "Quick Sort":
                    panel.quickSort();
                    break;
            }
        });

        frame.add(comboBox, BorderLayout.NORTH);
    }
}