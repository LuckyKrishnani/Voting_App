import 'package:equatable/equatable.dart';

abstract class ElectionState extends Equatable {
  const ElectionState();

  @override
  List<Object> get props => [];
}

class ElectionInitial extends ElectionState {}

class ElectionLoading extends ElectionState {}

class ElectionLoaded extends ElectionState {
  final List<dynamic> elections;

  const ElectionLoaded(this.elections);

  @override
  List<Object> get props => [elections];
}

class ElectionError extends ElectionState {
  final String message;

  const ElectionError(this.message);

  @override
  List<Object> get props => [message];
}
