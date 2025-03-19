package ru.nelezin.storage.service;

public interface MinioBucketService {

    void createBucket(String bucketName) throws Exception;

    boolean isBucketExists(String bucketName) throws Exception;
}
