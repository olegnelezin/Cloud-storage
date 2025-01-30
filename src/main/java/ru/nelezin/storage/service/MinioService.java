package ru.nelezin.storage.service;

public interface MinioService {

    void createBucket(String bucketName) throws Exception;

    boolean isBucketExists(String bucketName) throws Exception;
}
