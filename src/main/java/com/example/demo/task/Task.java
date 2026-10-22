package com.example.demo.task;

import java.time.LocalDateTime;

public class Task {
        private Integer id;
        private String title;
        private String description;
        private boolean completed;
        private LocalDateTime createdAt;
        private LocalDateTime completedAt;

        public Task(Integer id, String title, String description, boolean isCompleted, LocalDateTime createdAt,
                        LocalDateTime completedAt) {
                this.id = id;
                this.title = title;
                this.description = description;
                this.completed = isCompleted;
                this.createdAt = createdAt;
                this.completedAt = completedAt;
        }

        public Integer getId() {
                return id;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public boolean isCompleted() {
                return completed;
        }

        public void setCompleted(boolean isCompleted) {
                this.completed = isCompleted;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }

        public LocalDateTime getCompletedAt() {
                return completedAt;
        }

        public void setCompletedAt(LocalDateTime completedAt) {
                this.completedAt = completedAt;
        }

}