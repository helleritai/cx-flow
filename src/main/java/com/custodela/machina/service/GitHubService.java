package com.custodela.machina.service;

import com.custodela.machina.config.GitHubProperties;
import com.custodela.machina.config.MachinaProperties;
import com.custodela.machina.dto.RepoIssue;
import com.custodela.machina.dto.ScanRequest;
import com.custodela.machina.dto.ScanResults;
import com.custodela.machina.exception.GitHubClienException;
import com.custodela.machina.utils.ScanUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Map;

@Service
public class GitHubService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(GitHubService.class);
    private final RestTemplate restTemplate;
    private final GitHubProperties properties;
    private final MachinaProperties machinaProperties;

    @ConstructorProperties({"restTemplate", "properties", "machinaProperties"})
    public GitHubService(RestTemplate restTemplate, GitHubProperties properties, MachinaProperties machinaProperties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
        this.machinaProperties = machinaProperties;
    }


    private HttpHeaders createAuthHeaders(){
        return new HttpHeaders() {{
            set("Authorization", "token ".concat(properties.getToken()));
        }};
    }

    Map<String, List<String>> process(ScanResults results, ScanRequest request) throws GitHubClienException {
        return null;
    }

    void processPull(ScanRequest request,ScanResults results) throws GitHubClienException {
        try {
            String comment = ScanUtils.getMergeCommentMD(request, results, machinaProperties);
            log.debug("comment: {}", comment);
            sendMergeComment(request, comment);
        } catch (HttpClientErrorException e){
            log.error("Error occurred while creating Merge Request comment");
            throw new GitHubClienException();
        }
    }

    void sendMergeComment(ScanRequest request, String comment){
        HttpEntity httpEntity = new HttpEntity<>(RepoIssue.getJSONComment("body",comment).toString(), createAuthHeaders());
        ResponseEntity<String> response = restTemplate.exchange(request.getMergeNoteUri(), HttpMethod.POST, httpEntity, String.class);
    }

    void startBlockMerge(ScanRequest request, String url){
        if(properties.isBlockMerge()) {
            HttpEntity httpEntity = new HttpEntity<>(
                    getJSONStatus("pending", url, "Checkmarx Scan Initiated").toString(),
                    createAuthHeaders()
            );
            if(ScanUtils.empty(request.getAdditionalMetadata("statuses_url"))){
                log.error("statuses_url was not provided within the request object, which is required for blocking / unblocking pull requests");
                return;
            }
            ResponseEntity<String> response = restTemplate.exchange(request.getAdditionalMetadata("statuses_url"),
                    HttpMethod.POST, httpEntity, String.class);
        }
    }

    void endBlockMerge(ScanRequest request, String url){
        if(properties.isBlockMerge()) {
            HttpEntity httpEntity = new HttpEntity<>(
                    getJSONStatus("success", url, "Checkmarx Scan Completed").toString(),
                    createAuthHeaders()
            );
            if(ScanUtils.empty(request.getAdditionalMetadata("statuses_url"))){
                log.error("statuses_url was not provided within the request object, which is required for blocking / unblocking pull requests");
                return;
            }
            ResponseEntity<String> response = restTemplate.exchange(request.getAdditionalMetadata("statuses_url"),
                    HttpMethod.POST, httpEntity, String.class);
        }
    }

    void failBlockMerge(ScanRequest request, String url){
        if(properties.isBlockMerge()) {
            HttpEntity httpEntity = new HttpEntity<>(
                    getJSONStatus("failure", url, "Checkmarx Issue Threshold Met").toString(),
                    createAuthHeaders()
            );
            if(ScanUtils.empty(request.getAdditionalMetadata("statuses_url"))){
                log.error("statuses_url was not provided within the request object, which is required for blocking / unblocking pull requests");
                return;
            }
            ResponseEntity<String> response = restTemplate.exchange(request.getAdditionalMetadata("statuses_url"),
                    HttpMethod.POST, httpEntity, String.class);
        }
    }

    private JSONObject getJSONStatus(String state, String url, String description){
        JSONObject requestBody = new JSONObject();
        requestBody.put("state", state);
        requestBody.put("target_url", url);
        requestBody.put("description", description);
        requestBody.put("context", "checkmarx");
        return requestBody;
    }
}