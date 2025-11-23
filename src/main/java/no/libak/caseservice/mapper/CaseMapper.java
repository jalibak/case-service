package no.libak.caseservice.mapper;

import no.libak.caseservice.api.CaseDto;
import no.libak.caseservice.api.CommentDto;
import no.libak.caseservice.api.NewCaseDto;
import no.libak.caseservice.api.NewCommentDto;
import no.libak.caseservice.constant.CaseStatus;
import no.libak.caseservice.model.Case;
import no.libak.caseservice.model.Comment;

import java.time.LocalDateTime;

public class CaseMapper {

    private CaseMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Case newCaseDtoToCase(NewCaseDto newCaseDto, String userName) {
        return Case.builder()
                .caseStatus(CaseStatus.OPEN)
                .description(newCaseDto.description())
                .caseType(newCaseDto.caseType())
                .createdBy(userName)
                .createdDate(LocalDateTime.now())
                .sensitive(false)
                .build();
    }

    public static CaseDto caseToCaseDto(Case aCase) {
        return new CaseDto(aCase.getCaseType(),
                aCase.getCaseStatus(),
                aCase.getCaseNumber(),
                aCase.getDescription(),
                aCase.getCreatedBy(),
                aCase.getCreatedDate(),
                aCase.getLastModifiedDate(),
                aCase.getAssignedUnit(),
                aCase.getAssignedUser(),
                aCase.getComments().stream()
                        .map(CaseMapper::commentToCommentDto)
                        .toList());
    }

    public static Comment newCommentDtoToComment(NewCommentDto newCommentDto, String username) {
        return Comment.builder()
                .comment(newCommentDto.comment())
                .createdBy(username)
                .build();
    }

    public static CommentDto commentToCommentDto(Comment comment) {
        return new CommentDto(comment.getComment(),
                comment.getCreatedBy(),
                comment.getCreatedDate());
    }
}
