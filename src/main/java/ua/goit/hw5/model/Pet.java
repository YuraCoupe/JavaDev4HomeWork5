package ua.goit.hw5.model;

import java.util.Set;

public class Pet {
    private long id;
    private Category category;
    private String name;
    private Set<String> photoUrls;
    private Set<Tag> tags;
    private PetStatus status;

    public Pet(long id, Category category, String name, Set<String> photoUrls, Set<Tag> tags, PetStatus status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(Set<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public PetStatus getStatus() {
        return status;
    }

    public void setStatus(PetStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", photoUrls=" + photoUrls +
                ", tags=" + tags +
                ", status=" + status.getStatus() +
                '}';
    }
}
