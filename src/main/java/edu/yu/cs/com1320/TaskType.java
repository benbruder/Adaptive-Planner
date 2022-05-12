package edu.yu.cs.com1320;

public enum TaskType {
    PROJECT {
        @Override
        public String toString() {
            return "Project";
        }
    },
    TEST {
        @Override
        public String toString() {
            return "Test";
        }
    }
}