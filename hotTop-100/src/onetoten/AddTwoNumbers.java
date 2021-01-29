package onetoten;
/**
 * 两数相加  逆序
 * 注意边界细节上的东西
 * / % 的用法
 * */
public class AddTwoNumbers {
    public class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode preHead = new ListNode();
            int jin = 0;
            int val = 0;
            ListNode temp = new ListNode();
            preHead = temp;
            while(l1 != null && l2 != null){
                temp.next = new ListNode();
                temp = temp.next;
                val = (l1.val + l2.val + jin)%10;
                jin = (l1.val + l2.val + jin)/10;
                temp.val = val;
                l1 = l1.next;
                l2 = l2.next;
            }
            while(l1 != null){
                temp.next = new ListNode();
                temp = temp.next;
                val = (l1.val  + jin)%10;
                jin = (l1.val + jin)/10;
                temp.val = val;

                l1 = l1.next;
            }
            while(l2 != null){
                temp.next = new ListNode();
                temp = temp.next;
                val = ( l2.val + jin)%10;
                jin = (l2.val + jin)/10;
                temp.val = val;
                l2 = l2.next;
            }
            if(jin == 1){
                temp.next = new ListNode(1);
            }
            return preHead.next;
        }

    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        ListNode head = null, tail = null;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int n1 = l1 != null ? l1.val : 0;
            int n2 = l2 != null ? l2.val : 0;
            int sum = n1 + n2 + carry;
            if (head == null) {
                head = tail = new ListNode(sum % 10);
            } else {
                tail.next = new ListNode(sum % 10);
                tail = tail.next;
            }
            carry = sum / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carry > 0) {
            tail.next = new ListNode(carry);
        }
        return head;
    }

}
