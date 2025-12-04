import 'package:dio/dio.dart';
import 'package:voting_bank/core/error/exceptions.dart';
import 'package:voting_bank/core/network/api_client.dart';
import 'package:voting_bank/data/models/vote.dart';

abstract class VoteRepository {
  Future<Vote> castVote(int electionId, VoteRequest voteRequest);
  Future<List<Vote>> getVotesByElection(int electionId);
  Future<bool> hasUserVoted(int electionId);
  Future<Map<String, int>> getVoteCounts(int electionId);
}

class VoteRepositoryImpl implements VoteRepository {
  final ApiClient apiClient;

  VoteRepositoryImpl({required this.apiClient});

  @override
  Future<Vote> castVote(int electionId, VoteRequest voteRequest) async {
    try {
      final response = await apiClient.post(
        '/elections/$electionId/vote',
        data: voteRequest.toJson(),
      );
      return Vote.fromJson(response.data['data']);
    } on DioException catch (e) {
      if (e.response?.statusCode == 409) {
        throw ServerException('You have already voted in this election');
      }
      throw ServerException(e.message ?? 'Failed to cast vote');
    }
  }

  @override
  Future<List<Vote>> getVotesByElection(int electionId) async {
    try {
      final response = await apiClient.get('/elections/$electionId/votes');
      final List<dynamic> data = response.data['data'] ?? [];
      return data.map((json) => Vote.fromJson(json)).toList();
    } on DioException catch (e) {
      throw ServerException(e.message ?? 'Failed to fetch votes');
    }
  }

  @override
  Future<bool> hasUserVoted(int electionId) async {
    try {
      final response = await apiClient.get('/elections/$electionId/has-voted');
      return response.data['data'] as bool? ?? false;
    } on DioException catch (e) {
      // If there's an error, assume user hasn't voted
      return false;
    }
  }

  @override
  Future<Map<String, int>> getVoteCounts(int electionId) async {
    try {
      final response = await apiClient.get('/elections/$electionId/results');
      final Map<String, dynamic> data = response.data['data'] ?? {};
      return data.map((key, value) => MapEntry(key, value as int));
    } on DioException catch (e) {
      throw ServerException(e.message ?? 'Failed to fetch vote counts');
    }
  }
}
