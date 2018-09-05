/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionnairePulseUpdateComponent } from 'app/entities/questionnaire-pulse/questionnaire-pulse-update.component';
import { QuestionnairePulseService } from 'app/entities/questionnaire-pulse/questionnaire-pulse.service';
import { QuestionnairePulse } from 'app/shared/model/questionnaire-pulse.model';

describe('Component Tests', () => {
    describe('QuestionnairePulse Management Update Component', () => {
        let comp: QuestionnairePulseUpdateComponent;
        let fixture: ComponentFixture<QuestionnairePulseUpdateComponent>;
        let service: QuestionnairePulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionnairePulseUpdateComponent]
            })
                .overrideTemplate(QuestionnairePulseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuestionnairePulseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionnairePulseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new QuestionnairePulse(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.questionnaire = entity;
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
                    const entity = new QuestionnairePulse();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.questionnaire = entity;
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
