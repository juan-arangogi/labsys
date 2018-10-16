/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { PacienteLabsysUpdateComponent } from 'app/entities/paciente-labsys/paciente-labsys-update.component';
import { PacienteLabsysService } from 'app/entities/paciente-labsys/paciente-labsys.service';
import { PacienteLabsys } from 'app/shared/model/paciente-labsys.model';

describe('Component Tests', () => {
    describe('PacienteLabsys Management Update Component', () => {
        let comp: PacienteLabsysUpdateComponent;
        let fixture: ComponentFixture<PacienteLabsysUpdateComponent>;
        let service: PacienteLabsysService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [PacienteLabsysUpdateComponent]
            })
                .overrideTemplate(PacienteLabsysUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PacienteLabsysUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PacienteLabsysService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PacienteLabsys(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.paciente = entity;
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
                    const entity = new PacienteLabsys();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.paciente = entity;
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
