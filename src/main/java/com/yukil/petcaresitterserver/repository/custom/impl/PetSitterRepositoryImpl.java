package com.yukil.petcaresitterserver.repository.custom.impl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yukil.petcaresitterserver.common.Querydsl4RepositorySupport;
import com.yukil.petcaresitterserver.dto.PetSitterQueryParam;
import com.yukil.petcaresitterserver.entity.*;
import com.yukil.petcaresitterserver.entity.QBankAccount;
import com.yukil.petcaresitterserver.entity.QPetSitter;
import com.yukil.petcaresitterserver.entity.QTimeAndArea;
import com.yukil.petcaresitterserver.repository.custom.PetSitterRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.yukil.petcaresitterserver.entity.QBankAccount.bankAccount;
import static com.yukil.petcaresitterserver.entity.QPetSitter.petSitter;
import static com.yukil.petcaresitterserver.entity.QTimeAndArea.timeAndArea;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class PetSitterRepositoryImpl extends Querydsl4RepositorySupport implements PetSitterRepositoryCustom {
    public PetSitterRepositoryImpl() {
        super(PetSitter.class);
    }

    @Override
    public Page<PetSitter> queryPetSitter(Pageable pageable, PetSitterQueryParam param) {

        Page<PetSitter> applyPagination = applyPagination(pageable, query -> query
                .selectFrom(petSitter)
                .distinct()
                .leftJoin(petSitter.bankAccount, bankAccount).fetchJoin()
                .leftJoin(petSitter.timeAndArea, timeAndArea).fetchJoin()
                .where(nameContains(param.getName()),
                        emailContains(param.getEmail()),
                        ageGoe(param.getAgeGoe()),
                        ageLoe(param.getAgeLoe()),
                        timeBetween(param.getFromTime(), param.getToTime()),
                        areaIn(param.getPossibleAreas())
                )
        );

        return applyPagination;
    }

    private BooleanExpression timeBetween(String fromTime, String toTime) {
        return petSitter.timeAndArea.fromTime.loe(LocalTime.parse(fromTime)).and(petSitter.timeAndArea.toTime.goe(LocalTime.parse(toTime)));
    }

    private BooleanExpression nameContains(String name) {
        return hasText(name) ? petSitter.name.contains(name) : null;
    }

    private BooleanExpression emailContains(String email) {
        return hasText(email) ? petSitter.email.contains(email) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe == null ? null : petSitter.birthday.loe(getBirthday(ageLoe));
    }
    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe == null ? null : petSitter.birthday.goe(getBirthday(ageGoe));
    }

    private LocalDate getBirthday(Integer age) {
        return LocalDate.of(LocalDate.now().getYear() - age, LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
    }

    private BooleanExpression fromTimeGoe(String fromTime) {
        return fromTime == null ? null : petSitter.timeAndArea.fromTime.goe(LocalTime.parse(fromTime));
    }

    private BooleanExpression toTimeLoe(String toTime) {
        return toTime == null ? null : petSitter.timeAndArea.toTime.loe(LocalTime.parse(toTime));
    }

    private BooleanExpression areaIn(PossibleArea possibleArea) {
        return possibleArea == null ? null : petSitter.timeAndArea.possibleArea.contains(possibleArea);
    }

}
