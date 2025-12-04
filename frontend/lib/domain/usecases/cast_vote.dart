import 'package:dartz/dartz.dart';
import 'package:equatable/equatable.dart';
import 'package:voting_bank/core/error/failures.dart';
import 'package:voting_bank/core/usecases/usecase.dart';
import 'package:voting_bank/domain/entities/vote.dart';
import 'package:voting_bank/domain/repositories/vote_repository.dart';

class CastVote implements UseCase<Vote, CastVoteParams> {
  final VoteRepository repository;

  CastVote(this.repository);

  @override
  Future<Either<Failure, Vote>> call(CastVoteParams params) async {
    return await repository.castVote(params.electionId, params.candidateId);
  }
}

class CastVoteParams extends Equatable {
  final int electionId;
  final int candidateId;

  const CastVoteParams({
    required this.electionId,
    required this.candidateId,
  });

  @override
  List<Object?> get props => [electionId, candidateId];
}
