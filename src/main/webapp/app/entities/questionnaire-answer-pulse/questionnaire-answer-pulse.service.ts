import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuestionnaireAnswerPulse } from 'app/shared/model/questionnaire-answer-pulse.model';

type EntityResponseType = HttpResponse<IQuestionnaireAnswerPulse>;
type EntityArrayResponseType = HttpResponse<IQuestionnaireAnswerPulse[]>;

@Injectable({ providedIn: 'root' })
export class QuestionnaireAnswerPulseService {
    private resourceUrl = SERVER_API_URL + 'api/questionnaire-answers';

    constructor(private http: HttpClient) {}

    create(questionnaireAnswer: IQuestionnaireAnswerPulse): Observable<EntityResponseType> {
        return this.http.post<IQuestionnaireAnswerPulse>(this.resourceUrl, questionnaireAnswer, { observe: 'response' });
    }

    update(questionnaireAnswer: IQuestionnaireAnswerPulse): Observable<EntityResponseType> {
        return this.http.put<IQuestionnaireAnswerPulse>(this.resourceUrl, questionnaireAnswer, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IQuestionnaireAnswerPulse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IQuestionnaireAnswerPulse[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
