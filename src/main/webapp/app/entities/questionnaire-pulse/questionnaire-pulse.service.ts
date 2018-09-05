import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuestionnairePulse } from 'app/shared/model/questionnaire-pulse.model';

type EntityResponseType = HttpResponse<IQuestionnairePulse>;
type EntityArrayResponseType = HttpResponse<IQuestionnairePulse[]>;

@Injectable({ providedIn: 'root' })
export class QuestionnairePulseService {
    private resourceUrl = SERVER_API_URL + 'api/questionnaires';

    constructor(private http: HttpClient) {}

    create(questionnaire: IQuestionnairePulse): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(questionnaire);
        return this.http
            .post<IQuestionnairePulse>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(questionnaire: IQuestionnairePulse): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(questionnaire);
        return this.http
            .put<IQuestionnairePulse>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IQuestionnairePulse>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuestionnairePulse[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(questionnaire: IQuestionnairePulse): IQuestionnairePulse {
        const copy: IQuestionnairePulse = Object.assign({}, questionnaire, {
            dateStart: questionnaire.dateStart != null && questionnaire.dateStart.isValid() ? questionnaire.dateStart.toJSON() : null,
            dateEnd: questionnaire.dateEnd != null && questionnaire.dateEnd.isValid() ? questionnaire.dateEnd.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateStart = res.body.dateStart != null ? moment(res.body.dateStart) : null;
        res.body.dateEnd = res.body.dateEnd != null ? moment(res.body.dateEnd) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((questionnaire: IQuestionnairePulse) => {
            questionnaire.dateStart = questionnaire.dateStart != null ? moment(questionnaire.dateStart) : null;
            questionnaire.dateEnd = questionnaire.dateEnd != null ? moment(questionnaire.dateEnd) : null;
        });
        return res;
    }
}
