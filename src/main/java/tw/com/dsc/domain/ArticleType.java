package tw.com.dsc.domain;

/**
 * 1.Application / Configuration Example
 * 	Required Fields: SCENARIO DESCRIPTION,SETUP/STEP BY STEP PROCEDURE, VERIFICATION
 * 2.General Info
 * 	Required Fields: QUESTION, ANSWER
 * 3.Issue
 * 	Required Fields: PROBLEM DESCRIPTION, SOLUTION,CONDITION/REPRODUCE PROCEDURE
 * 5.Spec. Info
 * 	Required Fields: eITS TICKET ID, QUESTION, ANSWER
 * 6.TroubleShooting
 * 	Required Fields: SCENARIO DESCRIPTION, SETUP/STEP BY STEP PROCEDURE, VERIFICATION
 * @author elliot
 *
 */
public enum ArticleType {
	Application, GeneralInfo, Issue, SpecInfo, TroubleShooting
}
