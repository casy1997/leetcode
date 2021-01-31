package onetoten;

import org.junit.Test;

/**
 * Median of Two Sorted Arrays
 * 寻找两个正序数组的中位数
 * 奇数:(length/2)  偶数:(length/2) + (length/2 -1)
 * 1. 归并排序中的 merge思想 ,合成一个新的有序数组 O(m+n) O(m+n)
 * 2. 因为知道长度 , 遍历到 中位数的位置 返回     O(m+n)  O(1)
 * 3. 找到第k小的数 , 每次比较,去掉某个数组前k/2, k = k - k/2  二分下去  O(log(m+n)) O(1)
 * 4. 对两个数组切割, 满足两个条件
 * 4.1 左半部分 = 右半部分  i + j = m - i + n - j
 * 4.2 左前半部分 <= 右后半部分
 * */
public class MedianOfTwoSortedArrays {
    /**
     * 方法二
     * */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        double result = 0;
        int l1 = nums1.length;
        int l2 = nums2.length;
        if(l1 == 0){
            return result = l2 % 2 == 0 ? (nums2[l2/2] + nums2[l2/2 - 1])/2.0 : nums2[l2/2];
        }
        if(l2 == 0){
            return result = l1 % 2 == 0 ? (nums1[l1/2] + nums1[l1/2 - 1])/2.0 : nums1[l1/2];
        }
        int cur = 0;
        int before = 0;
        int start1 = 0;
        int start2 = 0;
        boolean odd = (l1+l2) % 2 == 0? false : true;
        int median = (l1 + l2) / 2 + 1;
        int count = 0;
        for(int i = 0; i < median; i++ ){
            before = cur;
            if(start1 < l1 &&(start2 >= l2 || nums1[start1] < nums2[start2]) ){
                cur = nums1[start1++];
            }else{
                cur = nums2[start2++];
            }
        }
        return result = odd? cur :(cur + before)/2.0 ;
    }

    /**
     * 方法三
     * */
    public double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        int l1 = nums1.length;
        int l2 = nums2.length;
        /**
         * 奇数 : (5 + 1) / 2 = 3  (5 + 2) / 2 = 3
         * 偶数: (4 + 1) / 2 = 2 (4 + 2) / 2 = 3
         * */
        int k = (l1 + l2 + 1)/ 2;
        int k1 = (l1 + l2 + 2)/2;

        return (getMinK(nums1,nums2,k) + getMinK(nums1,nums2,k1))/2.0;
    }

    public double getMinK(int[] A, int[] B,int k){
        double result = 0;
        int l1 = A.length;
        int l2 = B.length;
        if (l1 == 0) {
            return result = l2 % 2 == 0 ? (B[l2 / 2] + B[l2 / 2 - 1]) / 2.0 : B[l2 / 2];
        }
        if (l2 == 0) {
            return result = l1 % 2 == 0 ? (A[l1 / 2] + A[l1 / 2 - 1]) / 2.0 : A[l1 / 2];
        }
        if (l1 == 1 && l2 == 1){
            return (A[0] + B[0])/2.0;
        }
        int start1 = -1;
        int start2 = -1;
        int v1 = 0;
        int v2 = 0;
        for (int i = k; i / 2 >= 0; i = i - i / 2) {
            /**
             * k == 1
             * */
            if(i/2 == 0){
                result = Math.min(A[start1 + 1], B[start2 + 1]);
                break;
            }
            /**
             * case1: 若 k/2(步长) >= l1 ,步长超过数组长度
             * 取数组尾元素值比较
             * 若 < , 该数组元素应全去除,此时直接可得到 中位数值 nums[k - l1 - 1]
             * 若> , 进入case2
             * */
            v1 = start1 + i / 2 + 1 >= l1 ? A[l1 - 1] : A[start1 + i/2];
            v2 = start2 + i / 2 + 1 >= l2 ? B[l2 - 1] : B[start2 + i/2];

            if(start1 + i / 2 + 1 >= l1 &&  A[l1 - 1] <= B[start2 + i/2]){
                return result = B[k - l1 - 1];
            }
            if(start2 + i / 2  + 1>= l2 &&  B[l2 - 1] <= A[start1 + i/2]){
                return result = A[k - l2 - 1];
            }
            /**
             * case2: 普通比较
             * v1 比 v2 ,谁小 ,谁往后面走
             * */
            if(v1 < v2){
                start1 += i/2;
            }else{
                start2 +=i/2;
            }
        }
        return result;
    }
    /**
     * 方法四
     *整体分割
     * */
    public double findMedianSortedArrays2(int[] A, int[] B){
        //A是较短的哪一个数组
        if(A.length > B.length){
            int[] temp = A;
            A = B;
            B = temp;
        }
        // m < n
        int m = A.length;
        int n = B.length;
        int left = 0, right = m;
        // median1：前一部分的最大值
        // median2：后一部分的最小值
        int median1 = 0, median2 = 0;
        while (left <= right) {
            // 前一部分包含 A[0 .. i-1] 和 B[0 .. j-1]
            // 后一部分包含 A[i .. m-1] 和 B[j .. n-1]
            /**
             *
             *
             * i+j=m−i+n−j  （当 m+n为偶数）或 i + j = m - i + n - j + 1（当 m+n 为奇数）。
             *  使用 i + j = m + n + 1 -i -j 可以统一奇偶
             **/
            int i = (left + right) / 2;
            int j = (m + n + 1) / 2 - i;

            // nums_im1, nums_i, nums_jm1, nums_j 分别表示 nums1[i-1], nums1[i], nums2[j-1], nums2[j]
            int nums_im1 = (i == 0 ? Integer.MIN_VALUE : A[i - 1]);
            int nums_i = (i == m ? Integer.MAX_VALUE : A[i]);
            int nums_jm1 = (j == 0 ? Integer.MIN_VALUE : B[j - 1]);
            int nums_j = (j == n ? Integer.MAX_VALUE : B[j]);

            /**
             * 左半部分最大的值小于等于右半部分最小的值 max ( A [ i - 1 ] , B [ j - 1 ]）） <= min ( A [ i ] , B [ j ]））
             * */
            //前半部分 < 后半部分
            if (nums_im1 <= nums_j) {
                median1 = Math.max(nums_im1, nums_jm1); // 前半部分最大的
                median2 = Math.min(nums_i, nums_j); // 后半部分最小的
                left = i + 1;
            } else {
                right = i - 1;
            }
        }
        return (m + n) % 2 == 0 ? (median1 + median2) / 2.0 : median1;

    }

    @Test
    public void test1(){
        int[] nums1 = new int[]{1,3,4,5};
        int[] nums2 = new int[]{-1};
        System.out.println(findMedianSortedArrays1(nums1,nums2));
    }
    @Test
    public void test2(){
        int[] nums1 = new int[]{7,7};
        int[] nums2 = new int[]{6,6};
        System.out.println(findMedianSortedArrays1(nums1,nums2));
    }
    @Test
    public void test3(){
        int[] nums1 = new int[]{1,6,7,8};
        int[] nums2 = new int[]{2,3,5,6,9};
        System.out.println(findMedianSortedArrays2(nums1,nums2));
    }
}
