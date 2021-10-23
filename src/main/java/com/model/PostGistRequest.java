/*
 * package com.model;
 * 
 * import com.fasterxml.jackson.annotation.JsonIgnoreProperties; import
 * com.fasterxml.jackson.annotation.JsonProperty; //import
 * lombok.AllArgsConstructor; //import lombok.Builder; //import lombok.Data;
 * //import lombok.NoArgsConstructor;
 * 
 * import java.util.HashMap;
 * 
 * public class PostGistRequest {
 * 
 * 
 * @JsonIgnoreProperties(ignoreUnknown = true) //@Builder public static class
 * GistDetails { private String description;
 * 
 * @JsonProperty("public") private boolean isPublic; private HashMap<String,
 * Files> files; }
 * 
 * 
 * @JsonProperty("description") private HashMap<String,Files> files;
 * 
 * @JsonProperty("description") private String description;
 * 
 * @JsonProperty("isPublic") private boolean isPublic;
 * 
 * @JsonProperty("description") public String getDescription() { return
 * description; }
 * 
 * @JsonProperty("description") public void setDescription(String description) {
 * this.description = description; }
 * 
 * @JsonProperty("isPublic") public boolean getIsPublic() { return isPublic; }
 * 
 * @JsonProperty("isPublic") public void setIsPublic(boolean isPublic) {
 * this.isPublic = isPublic; }
 * 
 * @JsonProperty("files") public HashMap<String, Files> getFiles() { return
 * files; }
 * 
 * @JsonProperty("files") public void setFiles(HashMap<String, Files> files) {
 * this.files = files; } }
 */