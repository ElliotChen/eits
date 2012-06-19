package tw.com.dsc.domain;

public enum Status {
	Draft,
	WaitForApproving,
	WaitForProofRead,
	ReadyToUpdate,
	ReadyToPublish,
	Published,
	WaitForRepublish,
	Archived,
	Deleted,
	LeaderReject,
	LeaderApproved,
	RejectToUpdate // only use for action
}
