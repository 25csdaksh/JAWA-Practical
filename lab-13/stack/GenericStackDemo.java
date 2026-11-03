package stack;

import java.util.EmptyStackException;

public class GenericStackDemo {

    public static class ArrayStack<T> {
        private Object[] elements;
        private int top;
        private static final int DEFAULT_CAPACITY = 10;

        public ArrayStack() {
            this(DEFAULT_CAPACITY);
        }

        public ArrayStack(int initialCapacity) {
            if (initialCapacity <= 0) {
                initialCapacity = DEFAULT_CAPACITY;
            }
            this.elements = new Object[initialCapacity];
            this.top = 0;
        }

        public void push(T item) {
            if (top == elements.length) {
                resize(elements.length * 2);
            }
            elements[top++] = item;
        }

        @SuppressWarnings("unchecked")
        public T pop() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            T item = (T) elements[--top];
            elements[top] = null;
            return item;
        }

        @SuppressWarnings("unchecked")
        public T peek() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            return (T) elements[top - 1];
        }

        public boolean isEmpty() {
            return top == 0;
        }

        public int size() {
            return top;
        }

        private void resize(int newCapacity) {
            Object[] newElements = new Object[newCapacity];
            System.arraycopy(elements, 0, newElements, 0, top);
            elements = newElements;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < top; i++) {
                sb.append(elements[i]);
                if (i < top - 1) sb.append(", ");
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println(" PRACTICAL 13 - PART A.3: GENERIC ARRAY-BACKED STACK");
        System.out.println("=============================================================");

        ArrayStack<String> stringStack = new ArrayStack<>(4);
        stringStack.push("First");
        stringStack.push("Second");
        stringStack.push("Third");

        System.out.println("String Stack after 3 pushes: " + stringStack);
        System.out.println("Top element (peek): " + stringStack.peek());
        System.out.println("Popped element: " + stringStack.pop());
        System.out.println("String Stack after pop: " + stringStack);
        System.out.println("String Stack size: " + stringStack.size());

        ArrayStack<Integer> intStack = new ArrayStack<>(3);
        intStack.push(10);
        intStack.push(20);
        intStack.push(30);
        intStack.push(40);

        System.out.println("\nInteger Stack after 4 pushes (with dynamic resize): " + intStack);
        while (!intStack.isEmpty()) {
            System.out.println("Popped from intStack: " + intStack.pop());
        }

        System.out.println("\nTesting Sensible Empty-Case Handling on empty stack:");
        try {
            intStack.pop();
        } catch (EmptyStackException e) {
            System.out.println("Caught EmptyStackException: Stack is empty as expected, preventing underflow!");
        }

        try {
            intStack.peek();
        } catch (EmptyStackException e) {
            System.out.println("Caught EmptyStackException on peek: Cannot inspect element on empty stack!");
        }
        System.out.println("=============================================================\n");
    }
}

