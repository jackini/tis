// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: preview-datax-records.proto

package com.qlangtech.tis.rpc.grpc.datax.preview;

public interface PreviewRowsDataResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:stream.PreviewRowsDataResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>map&lt;string, .stream.HeaderColGrpc&gt; columnHeader = 1;</code>
   */
  int getColumnHeaderCount();
  /**
   * <code>map&lt;string, .stream.HeaderColGrpc&gt; columnHeader = 1;</code>
   */
  boolean containsColumnHeader(
      java.lang.String key);
  /**
   * Use {@link #getColumnHeaderMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc>
  getColumnHeader();
  /**
   * <code>map&lt;string, .stream.HeaderColGrpc&gt; columnHeader = 1;</code>
   */
  java.util.Map<java.lang.String, com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc>
  getColumnHeaderMap();
  /**
   * <code>map&lt;string, .stream.HeaderColGrpc&gt; columnHeader = 1;</code>
   */

  com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc getColumnHeaderOrDefault(
      java.lang.String key,
      com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc defaultValue);
  /**
   * <code>map&lt;string, .stream.HeaderColGrpc&gt; columnHeader = 1;</code>
   */

  com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc getColumnHeaderOrThrow(
      java.lang.String key);

  /**
   * <code>repeated .stream.Record records = 2;</code>
   */
  java.util.List<com.qlangtech.tis.rpc.grpc.datax.preview.Record> 
      getRecordsList();
  /**
   * <code>repeated .stream.Record records = 2;</code>
   */
  com.qlangtech.tis.rpc.grpc.datax.preview.Record getRecords(int index);
  /**
   * <code>repeated .stream.Record records = 2;</code>
   */
  int getRecordsCount();
  /**
   * <code>repeated .stream.Record records = 2;</code>
   */
  java.util.List<? extends com.qlangtech.tis.rpc.grpc.datax.preview.RecordOrBuilder> 
      getRecordsOrBuilderList();
  /**
   * <code>repeated .stream.Record records = 2;</code>
   */
  com.qlangtech.tis.rpc.grpc.datax.preview.RecordOrBuilder getRecordsOrBuilder(
      int index);

  /**
   * <code>repeated .stream.OffsetColValGrpc headerCursor = 3;</code>
   */
  java.util.List<com.qlangtech.tis.rpc.grpc.datax.preview.OffsetColValGrpc> 
      getHeaderCursorList();
  /**
   * <code>repeated .stream.OffsetColValGrpc headerCursor = 3;</code>
   */
  com.qlangtech.tis.rpc.grpc.datax.preview.OffsetColValGrpc getHeaderCursor(int index);
  /**
   * <code>repeated .stream.OffsetColValGrpc headerCursor = 3;</code>
   */
  int getHeaderCursorCount();
  /**
   * <code>repeated .stream.OffsetColValGrpc headerCursor = 3;</code>
   */
  java.util.List<? extends com.qlangtech.tis.rpc.grpc.datax.preview.OffsetColValGrpcOrBuilder> 
      getHeaderCursorOrBuilderList();
  /**
   * <code>repeated .stream.OffsetColValGrpc headerCursor = 3;</code>
   */
  com.qlangtech.tis.rpc.grpc.datax.preview.OffsetColValGrpcOrBuilder getHeaderCursorOrBuilder(
      int index);

  /**
   * <code>repeated .stream.OffsetColValGrpc tailerCursor = 4;</code>
   */
  java.util.List<com.qlangtech.tis.rpc.grpc.datax.preview.OffsetColValGrpc> 
      getTailerCursorList();
  /**
   * <code>repeated .stream.OffsetColValGrpc tailerCursor = 4;</code>
   */
  com.qlangtech.tis.rpc.grpc.datax.preview.OffsetColValGrpc getTailerCursor(int index);
  /**
   * <code>repeated .stream.OffsetColValGrpc tailerCursor = 4;</code>
   */
  int getTailerCursorCount();
  /**
   * <code>repeated .stream.OffsetColValGrpc tailerCursor = 4;</code>
   */
  java.util.List<? extends com.qlangtech.tis.rpc.grpc.datax.preview.OffsetColValGrpcOrBuilder> 
      getTailerCursorOrBuilderList();
  /**
   * <code>repeated .stream.OffsetColValGrpc tailerCursor = 4;</code>
   */
  com.qlangtech.tis.rpc.grpc.datax.preview.OffsetColValGrpcOrBuilder getTailerCursorOrBuilder(
      int index);
}
