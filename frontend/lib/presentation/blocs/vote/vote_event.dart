import 'package:equatable/equatable.dart';

abstract class VoteEvent extends Equatable {
  const VoteEvent();

  @override
  List<Object> get props => [];
}

class CastVoteEvent extends VoteEvent {
  final String electionId;
  final String candidateId;

  const CastVoteEvent(this.electionId, this.candidateId);

  @override
  List<Object> get props => [electionId, candidateId];
}
