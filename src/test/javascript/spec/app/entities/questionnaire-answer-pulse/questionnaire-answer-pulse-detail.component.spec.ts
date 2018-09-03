/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionnaireAnswerPulseDetailComponent } from 'app/entities/questionnaire-answer-pulse/questionnaire-answer-pulse-detail.component';
import { QuestionnaireAnswerPulse } from 'app/shared/model/questionnaire-answer-pulse.model';

describe('Component Tests', () => {
    describe('QuestionnaireAnswerPulse Management Detail Component', () => {
        let comp: QuestionnaireAnswerPulseDetailComponent;
        let fixture: ComponentFixture<QuestionnaireAnswerPulseDetailComponent>;
        const route = ({ data: of({ questionnaireAnswer: new QuestionnaireAnswerPulse(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionnaireAnswerPulseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(QuestionnaireAnswerPulseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionnaireAnswerPulseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.questionnaireAnswer).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
