/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionGroupPulseComponent } from 'app/entities/question-group-pulse/question-group-pulse.component';
import { QuestionGroupPulseService } from 'app/entities/question-group-pulse/question-group-pulse.service';
import { QuestionGroupPulse } from 'app/shared/model/question-group-pulse.model';

describe('Component Tests', () => {
    describe('QuestionGroupPulse Management Component', () => {
        let comp: QuestionGroupPulseComponent;
        let fixture: ComponentFixture<QuestionGroupPulseComponent>;
        let service: QuestionGroupPulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionGroupPulseComponent],
                providers: []
            })
                .overrideTemplate(QuestionGroupPulseComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuestionGroupPulseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionGroupPulseService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new QuestionGroupPulse(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.questionGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
