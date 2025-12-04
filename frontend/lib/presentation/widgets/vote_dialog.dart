import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:voting_bank/core/constants/colors.dart';
import 'package:voting_bank/core/constants/text_styles.dart';
import 'package:voting_bank/data/models/election.dart';
import 'package:voting_bank/presentation/blocs/vote/vote_bloc.dart';

class VoteDialog extends StatefulWidget {
  final Election election;

  const VoteDialog({
    super.key,
    required this.election,
  });

  @override
  State<VoteDialog> createState() => _VoteDialogState();
}

class _VoteDialogState extends State<VoteDialog> {
  int? _selectedCandidateId;

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text(
        'Vote for ${widget.election.title}',
        style: AppTextStyles.h3,
      ),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            'Select a candidate to vote for:',
            style: AppTextStyles.bodyMedium,
          ),
          const SizedBox(height: 16),
          ...widget.election.candidates.map((candidate) {
            return RadioListTile<int>(
              title: Text(
                candidate.name,
                style: AppTextStyles.bodyMedium,
              ),
              subtitle: Text(
                candidate.description,
                style: AppTextStyles.caption,
              ),
              value: candidate.id,
              groupValue: _selectedCandidateId,
              onChanged: (value) {
                setState(() {
                  _selectedCandidateId = value;
                });
              },
              activeColor: AppColors.primary,
            );
          }),
        ],
      ),
      actions: [
        TextButton(
          onPressed: () => Navigator.of(context).pop(),
          child: Text(
            'Cancel',
            style: AppTextStyles.buttonMedium.copyWith(
              color: AppColors.textSecondary,
            ),
          ),
        ),
        BlocConsumer<VoteBloc, VoteState>(
          listener: (context, state) {
            if (state is VoteSuccess) {
              Navigator.of(context).pop();
              ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(
                  content: Text(
                    'Vote cast successfully!',
                    style: AppTextStyles.bodyMedium.copyWith(
                      color: Colors.white,
                    ),
                  ),
                  backgroundColor: AppColors.success,
                ),
              );
            } else if (state is VoteError) {
              ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(
                  content: Text(
                    state.message,
                    style: AppTextStyles.bodyMedium.copyWith(
                      color: Colors.white,
                    ),
                  ),
                  backgroundColor: AppColors.error,
                ),
              );
            }
          },
          builder: (context, state) {
            return ElevatedButton(
              onPressed: _selectedCandidateId == null || state is VoteLoading
                  ? null
                  : () {
                      context.read<VoteBloc>().add(
                            CastVoteEvent(
                              electionId: widget.election.id,
                              candidateId: _selectedCandidateId!,
                            ),
                          );
                    },
              style: ElevatedButton.styleFrom(
                backgroundColor: AppColors.primary,
                disabledBackgroundColor: AppColors.textSecondary.withOpacity(0.3),
              ),
              child: state is VoteLoading
                  ? const SizedBox(
                      width: 20,
                      height: 20,
                      child: CircularProgressIndicator(
                        strokeWidth: 2,
                        valueColor: AlwaysStoppedAnimation<Color>(Colors.white),
                      ),
                    )
                  : Text(
                      'Cast Vote',
                      style: AppTextStyles.buttonMedium.copyWith(
                        color: Colors.white,
                      ),
                    ),
            );
          },
        ),
      ],
    );
  }
}
