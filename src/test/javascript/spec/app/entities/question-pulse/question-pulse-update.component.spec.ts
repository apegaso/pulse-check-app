/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionPulseUpdateComponent } from 'app/entities/question-pulse/question-pulse-update.component';
import { QuestionPulseService } from 'app/entities/question-pulse/question-pulse.service';
import { QuestionPulse } from 'app/shared/model/question-pulse.model';

describe('Component Tests', () => {
    describe('QuestionPulse Management Update Component', () => {
        let comp: QuestionPulseUpdateComponent;
        let fixture: ComponentFixture<QuestionPulseUpdateComponent>;
        let service: QuestionPulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionPulseUpdateComponent]
            })
                .overrideTemplate(QuestionPulseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuestionPulseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionPulseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new QuestionPulse(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.question = entity;
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
                    const entity = new QuestionPulse();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.question = entity;
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
