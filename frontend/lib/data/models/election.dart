class Election {
  final String id;
  final String title;
  final String description;
  final DateTime startDate;
  final DateTime endDate;
  final List<Map<String, String>> candidates;
  final String status;

  Election({
    required this.id,
    required this.title,
    required this.description,
    required this.startDate,
    required this.endDate,
    required this.candidates,
    required this.status,
  });

  factory Election.fromJson(Map<String, dynamic> json) {
    return Election(
      id: json['id'],
      title: json['title'],
      description: json['description'],
      startDate: DateTime.parse(json['startDate']),
      endDate: DateTime.parse(json['endDate']),
      candidates: List<Map<String, String>>.from(json['candidates']),
      status: json['status'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'title': title,
      'description': description,
      'startDate': startDate.toIso8601String(),
      'endDate': endDate.toIso8601String(),
      'candidates': candidates,
      'status': status,
    };
  }
}
