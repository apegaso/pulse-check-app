/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionGroupPulseUpdateComponent } from 'app/entities/question-group-pulse/question-group-pulse-update.component';
import { QuestionGroupPulseService } from 'app/entities/question-group-pulse/question-group-pulse.service';
import { QuestionGroupPulse } from 'app/shared/model/question-group-pulse.model';

describe('Component Tests', () => {
    describe('QuestionGroupPulse Management Update Component', () => {
        let comp: QuestionGroupPulseUpdateComponent;
        let fixture: ComponentFixture<QuestionGroupPulseUpdateComponent>;
        let service: QuestionGroupPulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionGroupPulseUpdateComponent]
            })
                .overrideTemplate(QuestionGroupPulseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuestionGroupPulseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionGroupPulseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new QuestionGroupPulse(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.questionGroup = entity;
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
                    const entity = new QuestionGroupPulse();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.questionGroup = entity;
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
