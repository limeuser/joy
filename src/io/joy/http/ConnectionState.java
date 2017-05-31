package io.joy.http;

public enum ConnectionState {
	Start,
	ReadingRequest,
	Request,
	SetResponse,
	SendedChunkedHeader,
	SendedChunkedBody,
	Sended
	;
}
