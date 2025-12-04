import 'package:equatable/equatable.dart';

class Vote extends Equatable {
  final int id;
  final int electionId;
  final int voterId;
  final int candidateId;
  final DateTime votedAt;
  final String? ipAddress;
  final String? userAgent;
  final bool verified;

  const Vote({
    required this.id,
    required this.electionId,
    required this.voterId,
    required this.candidateId,
    required this.votedAt,
    this.ipAddress,
    this.userAgent,
    required this.verified,
  });

  factory Vote.fromJson(Map<String, dynamic> json) {
    return Vote(
      id: json['id'] as int,
      electionId: json['electionId'] as int,
      voterId: json['voterId'] as int,
      candidateId: json['candidateId'] as int,
      votedAt: DateTime.parse(json['votedAt'] as String),
      ipAddress: json['ipAddress'] as String?,
      userAgent: json['userAgent'] as String?,
      verified: json['verified'] as bool? ?? true,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'electionId': electionId,
      'voterId': voterId,
      'candidateId': candidateId,
      'votedAt': votedAt.toIso8601String(),
      'ipAddress': ipAddress,
      'userAgent': userAgent,
      'verified': verified,
    };
  }

  @override
  List<Object?> get props => [
    id, electionId, voterId, candidateId, votedAt, ipAddress, userAgent, verified
  ];
}

class VoteRequest extends Equatable {
  final int candidateId;

  const VoteRequest({
    required this.candidateId,
  });

  Map<String, dynamic> toJson() {
    return {
      'candidateId': candidateId,
    };
  }

  @override
  List<Object?> get props => [candidateId];
}
