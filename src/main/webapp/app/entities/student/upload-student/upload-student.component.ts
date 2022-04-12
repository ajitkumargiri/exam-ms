/* eslint-disable no-console */
/* eslint-disable @typescript-eslint/prefer-optional-chain */
/* eslint-disable @angular-eslint/component-selector */
import { Component, OnInit } from '@angular/core';

import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StudentService } from '../service/student.service';

@Component({
  selector: 'app-upload-student',
  templateUrl: './upload-student.component.html',
  styleUrls: ['./upload-student.component.css'],
})
export class UploadStudentComponent implements OnInit {
  selectedFiles?: FileList;
  currentFile?: File;
  progress = 0;
  message = '';

  fileInfos?: Observable<any>;

  constructor(private studentService: StudentService) {}

  ngOnInit(): void {
    this.fileInfos = this.studentService.getFiles();
  }

  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
  }

  upload(): void {
    this.progress = 0;

    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);

      if (file) {
        this.currentFile = file;

        this.studentService.upload(this.currentFile).subscribe(
          (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress = Math.round((100 * event.loaded) / event.total);
            } else if (event instanceof HttpResponse) {
              this.message = event.body.message;
              this.fileInfos = this.studentService.getFiles();
            }
          },
          (err: any) => {
            console.log(err);
            this.progress = 0;

            if (err.error && err.error.message) {
              this.message = err.error.message;
            } else {
              this.message = 'Could not upload the file!';
            }

            this.currentFile = undefined;
          }
        );
      }

      this.selectedFiles = undefined;
    }
  }

  previousState(): void {
    window.history.back();
  }
}
