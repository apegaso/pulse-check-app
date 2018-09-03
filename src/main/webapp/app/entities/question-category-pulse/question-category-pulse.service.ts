import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuestionCategoryPulse } from 'app/shared/model/question-category-pulse.model';

type EntityResponseType = HttpResponse<IQuestionCategoryPulse>;
type EntityArrayResponseType = HttpResponse<IQuestionCategoryPulse[]>;

@Injectable({ providedIn: 'root' })
export class QuestionCategoryPulseService {
    private resourceUrl = SERVER_API_URL + 'api/question-categories';

    constructor(private http: HttpClient) {}

    create(questionCategory: IQuestionCategoryPulse): Observable<EntityResponseType> {
        return this.http.post<IQuestionCategoryPulse>(this.resourceUrl, questionCategory, { observe: 'response' });
    }

    update(questionCategory: IQuestionCategoryPulse): Observable<EntityResponseType> {
        return this.http.put<IQuestionCategoryPulse>(this.resourceUrl, questionCategory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IQuestionCategoryPulse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IQuestionCategoryPulse[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
