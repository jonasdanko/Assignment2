


public class GenericArrayStack<E> implements Stack<E> {

    // ADD YOUR INSTANCE VARIABLES HERE
    private int top;
    private E[] stackArray;


    // Constructor
    public GenericArrayStack( int capacity ) {

        // ADD YOU CODE HERE
        top=0;
        stackArray = (E[]) new Object[capacity];

    }

    // Returns true if this ArrayStack is empty
    public boolean isEmpty() {

        // ADD YOU CODE HERE
        return top == 0;

    }

    public void push( E elem ) {

        // ADD YOU CODE HERE
        stackArray[top] = elem;
        ++top;

    }
    public E pop() {

        // ADD YOU CODE HERE
        E temp;
        --top;
        temp = stackArray[top];
        stackArray[top] = null;
        return temp;


    }

    public E peek() {

        // ADD YOU CODE HERE
        return stackArray[top-1];

    }
}
