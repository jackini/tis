// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: preview-datax-records.proto

package com.qlangtech.tis.rpc.grpc.datax.preview;

/**
 * Protobuf type {@code stream.HeaderColGrpc}
 */
public  final class HeaderColGrpc extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:stream.HeaderColGrpc)
    HeaderColGrpcOrBuilder {
private static final long serialVersionUID = 0L;
  // Use HeaderColGrpc.newBuilder() to construct.
  private HeaderColGrpc(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private HeaderColGrpc() {
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private HeaderColGrpc(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {

            index_ = input.readUInt32();
            break;
          }
          case 16: {

            blob_ = input.readBool();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.qlangtech.tis.rpc.grpc.datax.preview.DataXRecordsPreviewService.internal_static_stream_HeaderColGrpc_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.qlangtech.tis.rpc.grpc.datax.preview.DataXRecordsPreviewService.internal_static_stream_HeaderColGrpc_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc.class, com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc.Builder.class);
  }

  public static final int INDEX_FIELD_NUMBER = 1;
  private int index_;
  /**
   * <code>uint32 index = 1;</code>
   */
  public int getIndex() {
    return index_;
  }

  public static final int BLOB_FIELD_NUMBER = 2;
  private boolean blob_;
  /**
   * <code>bool blob = 2;</code>
   */
  public boolean getBlob() {
    return blob_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (index_ != 0) {
      output.writeUInt32(1, index_);
    }
    if (blob_ != false) {
      output.writeBool(2, blob_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (index_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(1, index_);
    }
    if (blob_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(2, blob_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc)) {
      return super.equals(obj);
    }
    com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc other = (com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc) obj;

    if (getIndex()
        != other.getIndex()) return false;
    if (getBlob()
        != other.getBlob()) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + INDEX_FIELD_NUMBER;
    hash = (53 * hash) + getIndex();
    hash = (37 * hash) + BLOB_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getBlob());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code stream.HeaderColGrpc}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:stream.HeaderColGrpc)
      com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpcOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.qlangtech.tis.rpc.grpc.datax.preview.DataXRecordsPreviewService.internal_static_stream_HeaderColGrpc_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.qlangtech.tis.rpc.grpc.datax.preview.DataXRecordsPreviewService.internal_static_stream_HeaderColGrpc_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc.class, com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc.Builder.class);
    }

    // Construct using com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      index_ = 0;

      blob_ = false;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.qlangtech.tis.rpc.grpc.datax.preview.DataXRecordsPreviewService.internal_static_stream_HeaderColGrpc_descriptor;
    }

    @java.lang.Override
    public com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc getDefaultInstanceForType() {
      return com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc.getDefaultInstance();
    }

    @java.lang.Override
    public com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc build() {
      com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc buildPartial() {
      com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc result = new com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc(this);
      result.index_ = index_;
      result.blob_ = blob_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc) {
        return mergeFrom((com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc other) {
      if (other == com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc.getDefaultInstance()) return this;
      if (other.getIndex() != 0) {
        setIndex(other.getIndex());
      }
      if (other.getBlob() != false) {
        setBlob(other.getBlob());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int index_ ;
    /**
     * <code>uint32 index = 1;</code>
     */
    public int getIndex() {
      return index_;
    }
    /**
     * <code>uint32 index = 1;</code>
     */
    public Builder setIndex(int value) {
      
      index_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 index = 1;</code>
     */
    public Builder clearIndex() {
      
      index_ = 0;
      onChanged();
      return this;
    }

    private boolean blob_ ;
    /**
     * <code>bool blob = 2;</code>
     */
    public boolean getBlob() {
      return blob_;
    }
    /**
     * <code>bool blob = 2;</code>
     */
    public Builder setBlob(boolean value) {
      
      blob_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool blob = 2;</code>
     */
    public Builder clearBlob() {
      
      blob_ = false;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:stream.HeaderColGrpc)
  }

  // @@protoc_insertion_point(class_scope:stream.HeaderColGrpc)
  private static final com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc();
  }

  public static com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<HeaderColGrpc>
      PARSER = new com.google.protobuf.AbstractParser<HeaderColGrpc>() {
    @java.lang.Override
    public HeaderColGrpc parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new HeaderColGrpc(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<HeaderColGrpc> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<HeaderColGrpc> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.qlangtech.tis.rpc.grpc.datax.preview.HeaderColGrpc getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

