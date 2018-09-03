/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionnaireAnswerPulseUpdateComponent } from 'app/entities/questionnaire-answer-pulse/questionnaire-answer-pulse-update.component';
import { QuestionnaireAnswerPulseService } from 'app/entities/questionnaire-answer-pulse/questionnaire-answer-pulse.service';
import { QuestionnaireAnswerPulse } from 'app/shared/model/questionnaire-answer-pulse.model';

describe('Component Tests', () => {
    describe('QuestionnaireAnswerPulse Management Update Component', () => {
        let comp: QuestionnaireAnswerPulseUpdateComponent;
        let fixture: ComponentFixture<QuestionnaireAnswerPulseUpdateComponent>;
        let service: QuestionnaireAnswerPulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionnaireAnswerPulseUpdateComponent]
            })
                .overrideTemplate(QuestionnaireAnswerPulseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuestionnaireAnswerPulseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionnaireAnswerPulseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new QuestionnaireAnswerPulse(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.questionnaireAnswer = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new QuestionnaireAnswerPulse();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.questionnaireAnswer = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
