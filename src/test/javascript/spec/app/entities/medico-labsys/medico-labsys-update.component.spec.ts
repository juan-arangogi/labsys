/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { MedicoLabsysUpdateComponent } from 'app/entities/medico-labsys/medico-labsys-update.component';
import { MedicoLabsysService } from 'app/entities/medico-labsys/medico-labsys.service';
import { MedicoLabsys } from 'app/shared/model/medico-labsys.model';

describe('Component Tests', () => {
    describe('MedicoLabsys Management Update Component', () => {
        let comp: MedicoLabsysUpdateComponent;
        let fixture: ComponentFixture<MedicoLabsysUpdateComponent>;
        let service: MedicoLabsysService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [MedicoLabsysUpdateComponent]
            })
                .overrideTemplate(MedicoLabsysUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MedicoLabsysUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedicoLabsysService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MedicoLabsys(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.medico = entity;
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
                    const entity = new MedicoLabsys();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.medico = entity;
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
