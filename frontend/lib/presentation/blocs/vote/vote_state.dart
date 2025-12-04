import 'package:equatable/equatable.dart';

abstract class VoteState extends Equatable {
  const VoteState();

  @override
  List<Object> get props => [];
}

class VoteInitial extends VoteState {}

class VoteLoading extends VoteState {}

class VoteSuccess extends VoteState {}

class VoteError extends VoteState {
  final String message;

  const VoteError(this.message);

  @override
  List<Object> get props => [message];
}
